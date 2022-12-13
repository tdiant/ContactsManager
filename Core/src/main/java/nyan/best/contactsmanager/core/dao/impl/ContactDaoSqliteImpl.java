package nyan.best.contactsmanager.core.dao.impl;

import nyan.best.contactsmanager.common.entity.*;
import nyan.best.contactsmanager.core.dao.ContactDao;
import nyan.best.contactsmanager.core.dao.InstanceMessageItemDao;
import nyan.best.contactsmanager.core.dao.PersonDateItemDao;
import nyan.best.contactsmanager.core.dao.PhoneItemDao;
import nyan.best.contactsmanager.core.service.DatabaseConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ContactDaoSqliteImpl implements ContactDao {

    private final DatabaseConnectionService databaseSrv;

    private final InstanceMessageItemDao instanceMsgsDao;
    private final PersonDateItemDao personDateDao;
    private final PhoneItemDao phoneItemDao;

    public ContactDaoSqliteImpl(DatabaseConnectionService databaseSrv, InstanceMessageItemDao instanceMsgsDao, PersonDateItemDao personDateDao, PhoneItemDao phoneItemDao) {
        this.databaseSrv = databaseSrv;
        this.instanceMsgsDao = instanceMsgsDao;
        this.personDateDao = personDateDao;
        this.phoneItemDao = phoneItemDao;
    }

    @Override
    public Contact getContactByUUID(UUID uuid) {
        Contact contact = null;
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select * from contacts where uuid=?")) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");

                List<PhoneItem> phones = Arrays.stream(rs.getString("phones").split(","))
                        .filter(x -> !x.isEmpty())
                        .map(Long::parseLong)
                        .map(phoneItemDao::getPhoneItemById)
                        .toList();
                List<String> emails = Arrays.stream(rs.getString("emails").split(","))
                        .filter(x -> !x.isEmpty())
                        .toList();
                List<PersonDateItem> personDates = Arrays.stream(rs.getString("dates").split(","))
                        .filter(x -> !x.isEmpty())
                        .map(Long::parseLong)
                        .map(personDateDao::getPersonDateItemById)
                        .toList();
                List<InstanceMessageItem> instanceMsgs = Arrays.stream(rs.getString("instance_msgs").split(","))
                        .filter(x -> !x.isEmpty())
                        .map(Long::parseLong)
                        .map(instanceMsgsDao::getInstanceMessageItemById)
                        .toList();
                String notes = rs.getString("notes");

                contact = new Contact(uuid, name, phones, emails, personDates, instanceMsgs, notes);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contact;
    }

    @Override
    public void insertContact(Contact contact) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("insert into contacts(uuid,name,phones,emails,dates,instance_msgs,notes) values (?,?,?,?,?,?,?);")) {
            ps.setString(1, contact.getUuid().toString());
            ps.setString(2, contact.getName());
            ps.setString(3, String.join(",", contact.getPhones().stream()
                            .map(x -> x.getId() + "")
                            .toList())
                    .replace(" ", ""));
            ps.setString(4, String.join(",", contact.getEmails()));
            ps.setString(5, String.join(",", contact.getDates().stream()
                            .map(x -> x.getId() + "")
                            .toList())
                    .replace(" ", ""));
            ps.setString(6, String.join(",", contact.getInstanceMessages().stream()
                            .map(x -> x.getId() + "")
                            .toList())
                    .replace(" ", ""));
            ps.setString(7, contact.getNotes());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeContact(UUID uuid) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("delete from contacts where uuid=?;")) {
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateContact(Contact contact) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement(
                "update contacts set name=?, phones=?, emails=?, dates=?, instance_msgs=?, notes=? where uuid=?;"
        )) {
            ps.setString(1, contact.getName());
            ps.setString(2, String.join(",", contact.getPhones().stream()
                            .map(x -> x.getId() + "")
                            .toList())
                    .replace(" ", ""));
            ps.setString(3, String.join(",", contact.getEmails()));
            ps.setString(4, String.join(",", contact.getDates().stream()
                            .map(x -> x.getId() + "")
                            .toList())
                    .replace(" ", ""));
            ps.setString(5, String.join(",", contact.getInstanceMessages().stream()
                            .map(x -> x.getId() + "")
                            .toList())
                    .replace(" ", ""));
            ps.setString(6, contact.getNotes());
            ps.setString(7, contact.getUuid().toString());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FakeContact getFakeContactByUUID(UUID uuid) {
        FakeContact fc = null;
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select name from contacts where uuid=?")) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                fc = new FakeContact(uuid, name);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fc;
    }

    @Override
    public List<FakeContact> findFakeContactsByKeyword(String keyword) {
        keyword = keyword.replace("%", "").replace("_", "");
        Set<UUID> uuidSet = new HashSet<>();
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select uuid from contacts where name like ?")) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                uuidSet.add(UUID.fromString(rs.getString("uuid")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (uuidSet.size() < 20)
            instanceMsgsDao.findByKeyword(keyword).forEach(x -> uuidSet.add(x.getOwnerContact()));
        if (uuidSet.size() < 20)
            personDateDao.findByKeyword(keyword).forEach(x -> uuidSet.add(x.getOwnerContact()));
        if (uuidSet.size() < 20)
            phoneItemDao.findByKeyword(keyword).forEach(x -> uuidSet.add(x.getOwnerContact()));

        return uuidSet.stream()
                .map(this::getFakeContactByUUID)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<FakeContact> findFakeContactsByPage(int page, int cntPerPage) {
        List<FakeContact> ls = new ArrayList<>();
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement(
                "select uuid,name from contacts limit " + cntPerPage + " offset " + ((page - 1) * cntPerPage)
        )) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                FakeContact fc = new FakeContact(uuid, name);
                ls.add(fc);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }
}

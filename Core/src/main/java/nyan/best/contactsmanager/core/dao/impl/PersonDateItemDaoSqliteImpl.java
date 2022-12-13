package nyan.best.contactsmanager.core.dao.impl;

import nyan.best.contactsmanager.core.dao.PersonDateItemDao;
import nyan.best.contactsmanager.common.entity.PersonDateItem;
import nyan.best.contactsmanager.core.service.DatabaseConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonDateItemDaoSqliteImpl implements PersonDateItemDao {

    private final DatabaseConnectionService databaseSrv;

    public PersonDateItemDaoSqliteImpl(DatabaseConnectionService databaseSrv) {
        this.databaseSrv = databaseSrv;
    }

    @Override
    public PersonDateItem getPersonDateItemById(Long id) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select * from person_dates where id=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            PersonDateItem item = null;
            if (rs.next()) {
                item = new PersonDateItem(
                        rs.getLong("id"),
                        rs.getString("type"),
                        rs.getDate("date"),
                        UUID.fromString(rs.getString("owner_contact"))
                );
            }
            rs.close();
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertPersonDateItem(PersonDateItem item) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("insert into person_dates (id,type,date,owner_contact) values (?,?,?,?);")) {
            item.setId(databaseSrv.nextVal("person_dates"));
            ps.setLong(1, item.getId());
            ps.setString(2, item.getType());
            ps.setDate(3, item.getDate());
            ps.setString(4, item.getOwnerContact().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePersonDateItem(PersonDateItem item) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("update person_dates set type=?,date=? where id=?;")) {
            ps.setString(1, item.getType());
            ps.setDate(2, item.getDate());
            ps.setLong(3, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePersonDateItem(Long id) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("delete from person_dates where id=?;")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PersonDateItem> findByKeyword(String keyword) {
        return new ArrayList<>();
    }

}

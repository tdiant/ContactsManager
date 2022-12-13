package nyan.best.contactsmanager.core.dao.impl;

import nyan.best.contactsmanager.core.dao.PhoneItemDao;
import nyan.best.contactsmanager.common.entity.PhoneItem;
import nyan.best.contactsmanager.core.service.DatabaseConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhoneItemDaoSqliteImpl implements PhoneItemDao {

    private final DatabaseConnectionService databaseSrv;

    public PhoneItemDaoSqliteImpl(DatabaseConnectionService databaseSrv) {
        this.databaseSrv = databaseSrv;
    }

    @Override
    public PhoneItem getPhoneItemById(Long id) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select * from phones where id=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            PhoneItem item = null;
            if (rs.next()) {
                item = new PhoneItem(
                        rs.getLong("id"),
                        PhoneItem.PhoneType.valueOf(rs.getString("type")),
                        rs.getString("value"),
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
    public void insertPhoneItem(PhoneItem item) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("insert into phones (id,type,value,owner_contact) values (?,?,?,?);")) {
            item.setId(databaseSrv.nextVal("phones"));
            ps.setLong(1, item.getId());
            ps.setString(2, item.getType().name());
            ps.setString(3, item.getValue());
            ps.setString(4, item.getOwnerContact().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePhoneItem(PhoneItem item) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("update phones set type=?,value=? where id=?;")) {
            ps.setString(1, item.getType().name());
            ps.setString(2, item.getValue());
            ps.setLong(3, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePhoneItem(Long id) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("delete from phones where id=?;")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PhoneItem> findByKeyword(String keyword) {
        keyword = keyword.replace("%", "").replace("_", "");
        List<PhoneItem> ls = new ArrayList<>();
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select * from phones where value like ?")) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ls.add(new PhoneItem(
                        rs.getLong("id"),
                        PhoneItem.PhoneType.valueOf(rs.getString("type")),
                        rs.getString("value"),
                        UUID.fromString(rs.getString("owner_contact"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

}

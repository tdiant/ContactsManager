package nyan.best.contactsmanager.core.dao.impl;

import nyan.best.contactsmanager.common.entity.InstanceMessageItem;
import nyan.best.contactsmanager.core.dao.InstanceMessageItemDao;
import nyan.best.contactsmanager.core.service.DatabaseConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstanceMessageItemDaoSqliteImpl implements InstanceMessageItemDao {

    private final DatabaseConnectionService databaseSrv;

    public InstanceMessageItemDaoSqliteImpl(DatabaseConnectionService databaseSrv) {
        this.databaseSrv = databaseSrv;
    }

    @Override
    public InstanceMessageItem getInstanceMessageItemById(Long id) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select * from instance_msgs where id=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            InstanceMessageItem item = null;
            if (rs.next()) {
                item = new InstanceMessageItem(
                        rs.getLong("id"),
                        rs.getString("type"),
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
    public void insertInstanceMessage(InstanceMessageItem item) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("insert into instance_msgs (id,type,value,owner_contact) values (?,?,?,?);")) {
            item.setId(databaseSrv.nextVal("instant_msgs"));
            ps.setLong(1, item.getId());
            ps.setString(2, item.getType());
            ps.setString(3, item.getValue());
            ps.setString(4, item.getOwnerContact().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateInstanceMessage(InstanceMessageItem item) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("update instance_msgs set type=?,value=? where id=?;")) {
            ps.setString(1, item.getType());
            ps.setString(2, item.getValue());
            ps.setLong(3, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeInstanceMessage(Long id) {
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("delete from instance_msgs where id=?;")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InstanceMessageItem> findByKeyword(String keyword) {
        keyword = keyword.replace("%", "").replace("_", "");
        List<InstanceMessageItem> ls = new ArrayList<>();
        try (PreparedStatement ps = databaseSrv.conn().prepareStatement("select * from instance_msgs where value like ?")) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ls.add(new InstanceMessageItem(
                        rs.getLong("id"),
                        rs.getString("type"),
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

package nyan.best.contactsmanager.core.service.impl;

import nyan.best.contactsmanager.core.service.DatabaseConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnectionServiceSqliteImpl implements DatabaseConnectionService {

    private Connection conn;

    @Override
    public void init() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
        if (c == null) {
            throw new RuntimeException("Failed to link data.sqlite.");
        }
        conn = c;
    }

    @Override
    public Connection conn() {
        try {
            if (conn == null || conn.isClosed())
                init();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long nextVal(String key) {
        long x = 1L;
        try (PreparedStatement ps = conn.prepareStatement("select x from constant_x where key=?")) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                x = rs.getLong("x");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PreparedStatement ps = conn.prepareStatement("update constant_x set x=? where key=?;")) {
            ps.setLong(1, x + 1L);
            ps.setString(2, key);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

}

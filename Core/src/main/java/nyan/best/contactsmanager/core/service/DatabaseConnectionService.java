package nyan.best.contactsmanager.core.service;

import java.sql.Connection;

public interface DatabaseConnectionService {

    void init() throws Exception;

    Connection conn();

    long nextVal(String key);

}

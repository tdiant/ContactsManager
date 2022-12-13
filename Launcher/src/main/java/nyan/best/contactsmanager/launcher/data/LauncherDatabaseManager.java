package nyan.best.contactsmanager.launcher.data;

import nyan.best.contactsmanager.core.dao.ContactDao;
import nyan.best.contactsmanager.core.dao.InstanceMessageItemDao;
import nyan.best.contactsmanager.core.dao.PersonDateItemDao;
import nyan.best.contactsmanager.core.dao.PhoneItemDao;
import nyan.best.contactsmanager.core.dao.impl.ContactDaoSqliteImpl;
import nyan.best.contactsmanager.core.dao.impl.InstanceMessageItemDaoSqliteImpl;
import nyan.best.contactsmanager.core.dao.impl.PersonDateItemDaoSqliteImpl;
import nyan.best.contactsmanager.core.dao.impl.PhoneItemDaoSqliteImpl;
import nyan.best.contactsmanager.core.service.ContactService;
import nyan.best.contactsmanager.core.service.DatabaseConnectionService;
import nyan.best.contactsmanager.core.service.impl.ContactServiceSqliteImpl;
import nyan.best.contactsmanager.core.service.impl.DatabaseConnectionServiceSqliteImpl;
import nyan.best.contactsmanager.launcher.AppLauncher;

public class LauncherDatabaseManager {

    public static ContactService contactService;
    public static DatabaseConnectionService databaseConnectionService;
    public static ContactDao contactDao;
    public static InstanceMessageItemDao instanceMessageItemDao;
    public static PersonDateItemDao personDateItemDao;
    public static PhoneItemDao phoneItemDao;

    public static void init() {
        databaseConnectionService = new DatabaseConnectionServiceSqliteImpl();
        instanceMessageItemDao = new InstanceMessageItemDaoSqliteImpl(databaseConnectionService);
        personDateItemDao = new PersonDateItemDaoSqliteImpl(databaseConnectionService);
        phoneItemDao = new PhoneItemDaoSqliteImpl(databaseConnectionService);
        contactDao = new ContactDaoSqliteImpl(databaseConnectionService, instanceMessageItemDao, personDateItemDao, phoneItemDao);
        contactService = new ContactServiceSqliteImpl(
                contactDao,
                instanceMessageItemDao,
                personDateItemDao,
                phoneItemDao
        );
        System.out.println(contactService.list(1, 10));
    }

    public static void loadContacts() {
        AppLauncher.contactList.clear();
        AppLauncher.contactList.addAll(contactService.list(1, 200));
    }

}

package nyan.best.contactsmanager.core.service.impl;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.common.entity.FakeContact;
import nyan.best.contactsmanager.core.dao.ContactDao;
import nyan.best.contactsmanager.core.dao.InstanceMessageItemDao;
import nyan.best.contactsmanager.core.dao.PersonDateItemDao;
import nyan.best.contactsmanager.core.dao.PhoneItemDao;
import nyan.best.contactsmanager.core.service.ContactService;

import java.util.List;
import java.util.UUID;

public class ContactServiceSqliteImpl implements ContactService {

    private final ContactDao contactDao;
    private final InstanceMessageItemDao instanceMsgsDao;
    private final PersonDateItemDao personDateDao;
    private final PhoneItemDao phoneItemDao;

    public ContactServiceSqliteImpl(ContactDao contactDao, InstanceMessageItemDao instanceMsgsDao, PersonDateItemDao personDateDao, PhoneItemDao phoneItemDao) {
        this.contactDao = contactDao;
        this.instanceMsgsDao = instanceMsgsDao;
        this.personDateDao = personDateDao;
        this.phoneItemDao = phoneItemDao;
    }


    @Override
    public List<FakeContact> search(String keyword) {
        return contactDao.findFakeContactsByKeyword(keyword);
    }

    @Override
    public List<FakeContact> list(int page, int cntPerPage) {
        return contactDao.findFakeContactsByPage(page, cntPerPage);
    }

    @Override
    public Contact get(UUID uuid) {
        return contactDao.getContactByUUID(uuid);
    }

    @Override
    public void insert(Contact contact) {
        contact.getPhones().forEach(phoneItemDao::insertPhoneItem);
        contact.getDates().forEach(personDateDao::insertPersonDateItem);
        contact.getInstanceMessages().forEach(instanceMsgsDao::insertInstanceMessage);
        contactDao.insertContact(contact);
    }

    @Override
    public void update(Contact contact) {
        contact.getPhones().forEach(x -> {
            if (x.getId() != null && phoneItemDao.getPhoneItemById(x.getId()) != null)
                phoneItemDao.updatePhoneItem(x);
            else
                phoneItemDao.insertPhoneItem(x);
        });
        contact.getDates().forEach(x -> {
            if (x.getId() != null && personDateDao.getPersonDateItemById(x.getId()) != null)
                personDateDao.updatePersonDateItem(x);
            else
                personDateDao.insertPersonDateItem(x);
        });
        contact.getInstanceMessages().forEach(x -> {
            if (x.getId() != null && instanceMsgsDao.getInstanceMessageItemById(x.getId()) != null)
                instanceMsgsDao.updateInstanceMessage(x);
            else
                instanceMsgsDao.insertInstanceMessage(x);
        });
        contactDao.updateContact(contact);
    }

    @Override
    public void remove(Contact contact) {
        contact.getPhones().forEach(x -> phoneItemDao.removePhoneItem(x.getId()));
        contact.getDates().forEach(x -> personDateDao.removePersonDateItem(x.getId()));
        contact.getInstanceMessages().forEach(x -> instanceMsgsDao.removeInstanceMessage(x.getId()));
        contactDao.removeContact(contact.getUuid());
    }

}

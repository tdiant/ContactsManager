package nyan.best.contactsmanager.core.dao;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.common.entity.FakeContact;

import java.util.List;
import java.util.UUID;

public interface ContactDao {

    Contact getContactByUUID(UUID uuid);

    void insertContact(Contact contact);

    void removeContact(UUID uuid);

    void updateContact(Contact contact);

    FakeContact getFakeContactByUUID(UUID uuid);

    List<FakeContact> findFakeContactsByKeyword(String keyword);

    List<FakeContact> findFakeContactsByPage(int page, int cntPerPage);

}

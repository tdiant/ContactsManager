package nyan.best.contactsmanager.core.service;

import nyan.best.contactsmanager.common.entity.Contact;
import nyan.best.contactsmanager.common.entity.FakeContact;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    List<FakeContact> search(String keyword);

    List<FakeContact> list(int page, int cntPerPage);

    Contact get(UUID uuid);

    void insert(Contact contact);

    void update(Contact contact);

    void remove(Contact contact);

}

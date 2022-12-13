package nyan.best.contactsmanager.core.dao;

import nyan.best.contactsmanager.common.entity.PhoneItem;

import java.util.List;

public interface PhoneItemDao {

    PhoneItem getPhoneItemById(Long id);

    void insertPhoneItem(PhoneItem item);

    void updatePhoneItem(PhoneItem item);

    void removePhoneItem(Long id);

    List<PhoneItem> findByKeyword(String keyword);

}

package nyan.best.contactsmanager.core.dao;

import nyan.best.contactsmanager.common.entity.InstanceMessageItem;
import nyan.best.contactsmanager.common.entity.PersonDateItem;

import java.util.List;

public interface PersonDateItemDao {

    PersonDateItem getPersonDateItemById(Long id);

    void insertPersonDateItem(PersonDateItem item);

    void updatePersonDateItem(PersonDateItem item);

    void removePersonDateItem(Long id);

    List<PersonDateItem> findByKeyword(String keyword);

}

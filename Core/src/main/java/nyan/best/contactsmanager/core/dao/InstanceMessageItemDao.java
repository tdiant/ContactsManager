package nyan.best.contactsmanager.core.dao;

import nyan.best.contactsmanager.common.entity.InstanceMessageItem;

import java.util.List;

public interface InstanceMessageItemDao {

    InstanceMessageItem getInstanceMessageItemById(Long id);

    void insertInstanceMessage(InstanceMessageItem item);

    void updateInstanceMessage(InstanceMessageItem item);

    void removeInstanceMessage(Long id);

    List<InstanceMessageItem> findByKeyword(String keyword);

}

package nyan.best.contactsmanager.common.entity;

import java.util.UUID;

public class InstanceMessageItem {
    private Long id;
    private String type;
    private String value;
    private UUID ownerContact;

    public InstanceMessageItem(long id, String type, String value, UUID ownerContact) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.ownerContact = ownerContact;
    }

    public InstanceMessageItem(String type, String value, UUID ownerContact) {
        this.type = type;
        this.value = value;
        this.ownerContact = ownerContact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UUID getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(UUID ownerContact) {
        this.ownerContact = ownerContact;
    }

}

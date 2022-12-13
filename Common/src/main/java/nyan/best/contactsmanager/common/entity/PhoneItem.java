package nyan.best.contactsmanager.common.entity;

import java.util.UUID;

public class PhoneItem {
    private Long id;
    private PhoneType type;
    private String value;
    private UUID ownerContact;

    public PhoneItem(PhoneType type, String value, UUID ownerContact) {
        this.type = type;
        this.value = value;
        this.ownerContact = ownerContact;
    }

    public PhoneItem(long id, PhoneType type, String value, UUID ownerContact) {
        this.id = id;
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

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
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

    public enum PhoneType {
        MOBILE,
        HOME,
        WORK,
        SCHOOL,
        MAIN,
        FAX
    }

}

package nyan.best.contactsmanager.common.entity;

import java.sql.Date;
import java.util.UUID;

public class PersonDateItem {
    private Long id;
    private String type;
    private Date date;
    private UUID ownerContact;

    public PersonDateItem(String type, Date date, UUID ownerContact) {
        this.type = type;
        this.date = date;
        this.ownerContact = ownerContact;
    }

    public PersonDateItem(long id, String type, Date date, UUID ownerContact) {
        this.id = id;
        this.type = type;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(UUID ownerContact) {
        this.ownerContact = ownerContact;
    }

}

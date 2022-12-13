package nyan.best.contactsmanager.common.entity;

import java.util.List;
import java.util.UUID;

public class Contact {

    private UUID uuid;
    private String name;
    private List<PhoneItem> phones;
    private List<String> emails;
    private List<PersonDateItem> dates;

    private List<InstanceMessageItem> instanceMessages;
    private String notes;

    public Contact(UUID uuid, String name, List<PhoneItem> phones, List<String> emails, List<PersonDateItem> dates, List<InstanceMessageItem> instanceMessages, String notes) {
        this.uuid = uuid;
        this.name = name;
        this.phones = phones;
        this.emails = emails;
        this.dates = dates;
        this.instanceMessages = instanceMessages;
        this.notes = notes;
    }

    public Contact(String name, List<PhoneItem> phones, List<String> emails, List<PersonDateItem> dates, List<InstanceMessageItem> instanceMessages, String notes) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.phones = phones;
        this.emails = emails;
        this.dates = dates;
        this.instanceMessages = instanceMessages;
        this.notes = notes;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneItem> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneItem> phones) {
        this.phones = phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<PersonDateItem> getDates() {
        return dates;
    }

    public void setDates(List<PersonDateItem> dates) {
        this.dates = dates;
    }

    public List<InstanceMessageItem> getInstanceMessages() {
        return instanceMessages;
    }

    public void setInstanceMessages(List<InstanceMessageItem> instanceMessages) {
        this.instanceMessages = instanceMessages;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}

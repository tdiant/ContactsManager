package nyan.best.contactsmanager.common.entity;

import java.util.UUID;

public class FakeContact {

    private final UUID uuid;
    private final String name;

    public FakeContact(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "FakeContact{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}

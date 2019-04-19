package impl;

import common.MessageType;

import java.util.Objects;

public class SMS extends AbstractMessage  {

    public SMS() {
        super(MessageType.SMS);
    }

    public SMS(String firstRecipient) {
        this();
        getRecipients().add(firstRecipient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SMS sms = (SMS) o;
        return getType() == sms.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }
}

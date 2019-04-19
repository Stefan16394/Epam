package impl;

import common.MessageType;
import java.util.Objects;

public class MMS extends AttachmentMessageImpl {

    public MMS() {
       super(MessageType.MMS);
    }

    public MMS(String firstRecipient) {
        this();
        getRecipients().add(firstRecipient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MMS mms = (MMS) o;
        return getType() == mms.getType() &&
                Objects.equals(getAttachments(), mms.getAttachments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), getAttachments());
    }

}

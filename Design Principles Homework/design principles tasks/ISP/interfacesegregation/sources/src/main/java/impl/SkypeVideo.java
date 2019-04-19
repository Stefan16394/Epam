package impl;

import common.MessageType;
import java.util.Objects;

public class SkypeVideo extends AttachmentMessageImpl {

    public SkypeVideo() {
        super(MessageType.VIDEO);
    }

    public SkypeVideo(String firstRecipient) {
        this();
        getRecipients().add(firstRecipient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SkypeVideo that = (SkypeVideo) o;
        return getType() == that.getType() &&
                Objects.equals(getAttachments(), that.getAttachments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), getAttachments());
    }
}

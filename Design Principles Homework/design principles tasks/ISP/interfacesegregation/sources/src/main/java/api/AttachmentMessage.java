package api;

import common.Attachment;

import java.util.List;

public interface AttachmentMessage {
    List<Attachment> getAttachments();
    void setAttachments(List<Attachment> attachments);
}

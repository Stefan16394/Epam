package impl;

import api.AttachmentMessage;
import common.Attachment;
import common.MessageType;

import java.util.ArrayList;
import java.util.List;

public abstract class AttachmentMessageImpl extends AbstractMessage implements AttachmentMessage {
    private List<Attachment> attachments = new ArrayList<>();

    public AttachmentMessageImpl(MessageType type){
        super(type);
    }

    @Override
    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}

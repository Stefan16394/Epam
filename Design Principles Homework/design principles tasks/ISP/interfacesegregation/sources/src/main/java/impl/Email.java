package impl;

import api.EmailMessage;
import common.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Email extends AttachmentMessageImpl implements EmailMessage {

    private String subject;
    private List<String> ccRecipients = new ArrayList<>();
    private List<String> bccRecipients = new ArrayList<>();

    public Email() {
        super(MessageType.EMAIL);
    }

    public Email(String firstRecipient) {
        this();
        getRecipients().add(firstRecipient);
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public List<String> getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(List<String> ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    @Override
    public List<String> getBccRecipients() {
        return bccRecipients;
    }

    public void setBccRecipients(List<String> bccRecipients) {
        this.bccRecipients = bccRecipients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Email that = (Email) o;
        return getType() == that.getType() &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(ccRecipients, that.ccRecipients) &&
                Objects.equals(bccRecipients, that.bccRecipients) &&
                Objects.equals(getAttachments(), that.getAttachments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), subject, ccRecipients, bccRecipients, getAttachments());
    }
}
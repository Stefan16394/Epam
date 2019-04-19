package impl;

import api.FaxMessage;
import common.MessageType;

import java.util.Objects;

public class Fax extends AbstractMessage implements FaxMessage {

    private String companyName;
    private String callbackFax;
    private String subject;

    public Fax() {
        super(MessageType.FAX);
    }

    public Fax(String firstRecipient) {
        this();
        getRecipients().add(firstRecipient);
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getCallbackFax() {
        return callbackFax;
    }

    public void setCallbackFax(String callbackFax) {
        this.callbackFax = callbackFax;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Fax that = (Fax) o;
        return getType() == that.getType() &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(callbackFax, that.callbackFax) &&
                Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), companyName, callbackFax, subject);
    }

    @Override
    public String getSubject() {
        return this.subject;
    }
}

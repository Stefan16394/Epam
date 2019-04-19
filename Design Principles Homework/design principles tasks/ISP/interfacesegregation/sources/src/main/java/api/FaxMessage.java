package api;

public interface FaxMessage extends Message, SubjectMessage {

    String getCompanyName();

    String getCallbackFax();
}

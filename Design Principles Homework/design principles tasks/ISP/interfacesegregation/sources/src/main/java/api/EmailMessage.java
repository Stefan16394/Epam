package api;

import java.util.List;

public interface EmailMessage extends Message, SubjectMessage {

    List<String> getCcRecipients();

    List<String> getBccRecipients();
}

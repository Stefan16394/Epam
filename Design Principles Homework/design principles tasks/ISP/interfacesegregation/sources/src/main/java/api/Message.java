package api;

import common.MessageType;

import java.util.List;

public interface Message {

    List<String> getRecipients();

    String getMessageBody();

    MessageType getType();
}

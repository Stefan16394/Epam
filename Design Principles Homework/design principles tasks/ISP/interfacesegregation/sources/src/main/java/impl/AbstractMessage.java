package impl;

import api.Message;
import common.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractMessage implements Message {
    private MessageType messageType;
    private List<String> recipients = new ArrayList<>();
    private String messageBody;

    public AbstractMessage(){
    }

    public AbstractMessage(MessageType type){
       this.setMessageType(type);
    }

    @Override
    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public  void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getType() {
        return messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractMessage that = (AbstractMessage) o;

        if (!Objects.equals(recipients, that.recipients)) return false;
        return messageBody != null ? messageBody.equals(that.messageBody) : that.messageBody == null;
    }

    @Override
    public int hashCode() {
        int result = recipients != null ? recipients.hashCode() : 0;
        result = 31 * result + (messageBody != null ? messageBody.hashCode() : 0);
        return result;
    }
}

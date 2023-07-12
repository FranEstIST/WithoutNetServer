package tecnico.withoutnet.server.domain;

import javax.persistence.*;

@Entity
@Table(name = "messages",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"localId", "messageType", "timestamp", "sender", "receiver", "content"})})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long localId;

    private int messageType;

    private long timestamp;

    private String sender;

    private String receiver;

    private String content;

    public Message() {
    }

    public Message(long localId, int messageType, long timestamp, String sender, String receiver, String content) {
        this.localId = localId;
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Message(String messageString) {
        String messageStringComponents[] = messageString.split("#");

        if(messageStringComponents.length != 5) {
            //TODO: Throw an exception here
        }

        try {
            this.localId = Long.valueOf(messageStringComponents[0]);
            this.timestamp = Long.valueOf(messageStringComponents[1]);
        } catch (NumberFormatException e) {
            //TODO: Throw an exception here
        }

        this.sender = messageStringComponents[2];

        this.receiver = messageStringComponents[3];

        this.content = messageStringComponents[4];
    }

    public long getId() {
        return id;
    }

    public long getLocalId() {
        return localId;
    }

    public int getMessageType() {
        return messageType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

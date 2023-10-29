package tecnico.withoutnet.server.domain;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Table(name = "messages",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"timestamp", "messageType", "sender", "receiver", "payload"})})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private short length;

    private long timestamp;

    private int messageType;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Node sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Node receiver;

    private String payload;

    public Message() {
    }

    public Message(short length, long timestamp, int messageType, Node sender, Node receiver, String payload) {
        this.length = length;
        this.timestamp = timestamp;
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
    }

    /*public Message(String messageString) {
        String messageStringComponents[] = messageString.split("#");

        if(messageStringComponents.length != 5) {
            //TODO: Throw an exception here
        }

        try {
            this.length = Short.valueOf(messageStringComponents[0]);
            this.timestamp = Long.valueOf(messageStringComponents[1]);
            this.sender = Integer.valueOf(messageStringComponents[2]);
            this.receiver = Integer.valueOf(messageStringComponents[3]);
        } catch (NumberFormatException e) {
            //TODO: Throw an exception here
        }

        this.payload = messageStringComponents[4];
    }*/

    public long getId() {
        return id;
    }

    public short getLength() {
        return length;
    }

    public int getMessageType() {
        return messageType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Node getSender() {
        return sender;
    }

    public Node getReceiver() {
        return receiver;
    }

    public String getPayload() {
        return payload;
    }

    /*public String getPayloadAsString() {
        return Base64.getEncoder().encodeToString(payload);
    }*/

    public void setId(long id) {
        this.id = id;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }

    public void setReceiver(Node receiver) {
        this.receiver = receiver;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}

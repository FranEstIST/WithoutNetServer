package tecnico.withoutnet.server.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "updates")
public class Update implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long timestamp;

    @ManyToOne
    private Node sender;

    private String reading;

    public Update(long timestamp, Node sender, String reading) {
        this.timestamp = timestamp;
        this.sender = sender;
        this.reading = reading;
    }

    public Update(String updateString) {
        String updateStringComponents[] = updateString.split("#");

        if(updateStringComponents.length != 6) {
            //TODO: Throw an exception here
        }

        try {
            this.timestamp = Long.valueOf(updateStringComponents[1]);
        } catch (NumberFormatException e) {
            //TODO: Throw an exception here
        }

        this.sender = new Node(updateStringComponents[2], updateStringComponents[3], updateStringComponents[4]);

        this.reading = updateStringComponents[5];
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Node getSender() {
        return sender;
    }

    public String getReading() {
        return reading;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    @Override
    public String toString() {
        return "" + this.timestamp + "#" + this.sender + "#" + this.reading;
    }

}

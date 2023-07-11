package tecnico.withoutnet.server.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nodes")
public class Node implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uuid;

    private String commonName;
    
    private String readingType;

    public Node() {
    }

    public Node(String id, String commonName, String readingType) {
        this.setUuid(id);
        this.setCommonName(commonName);
        this.readingType = readingType;
    }

    public Node(String relevantNodeValue) {
        String[] relevantNodeValueComponents = relevantNodeValue.split("#");

        if(relevantNodeValueComponents.length != 3) {
            // TODO: Throw an exception
            return;
        }

        this.setUuid(relevantNodeValueComponents[0]);
        this.setCommonName(relevantNodeValueComponents[1]);
        this.setReadingType(relevantNodeValueComponents[2]);
    }

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String commonName) {
        if(commonName == null) {
            this.commonName = "Unnamed";
        } else {
            this.commonName = commonName;
        }
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        if(uuid == null) {
            this.uuid = "N/A";
        } else {
            this.uuid = uuid;
        }
    }

    public String getReadingType() {
        return this.readingType;
    }

    public void setReadingType(String readingType) {
        this.readingType = readingType;
    }

    @Override
    public String toString() {
        return "" + this.getUuid() + "#" + this.getCommonName() + "#" + this.getReadingType();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj != null && obj instanceof Node) {
            Node node = (Node) obj;
            return node.getUuid().equals(this.getUuid())
                    && node.getCommonName().equals(this.getCommonName())
                    && node.getReadingType().equals(this.getReadingType());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (this.getUuid()+this.getCommonName()+this.getReadingType()).hashCode();
    }
}

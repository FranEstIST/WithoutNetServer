package tecnico.withoutnet.server.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nodes")
public class Node implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String commonName;

    // TODO: Check if there are any issues with this field being nullable, i.e., with allowing
    // nodes to not be inside any network
    // Possible solution: Every node not in a network will be a "non-existing" network with id 0
    @ManyToOne()
    @JoinColumn(name = "network_id", nullable = true)
    private Network network;

    public Node() {
    }

    public Node(String commonName) {
        this.setCommonName(commonName);
    }

    public Node(int id, String commonName) {
        this.id = id;
        this.setCommonName(commonName);
    }

    public Node(String commonName, Network network) {
        this.setCommonName(commonName);
        this.setNetwork(network);
    }

    public Node(int id, String commonName, Network network) {
        this.id = id;
        this.setCommonName(commonName);
        this.setNetwork(network);
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

    public int getId() {
        return this.id;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return "" + this.getCommonName() + "#" + this.getId();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj != null && obj instanceof Node) {
            Node node = (Node) obj;
            return node.getId() == this.getId();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (this.getId()+this.getCommonName()).hashCode();
    }
}

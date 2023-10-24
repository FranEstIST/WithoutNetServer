package tecnico.withoutnet.server.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "networks")
public class Network {
    @Id
    private String name;

    @OneToMany(mappedBy = "network")
    @JoinColumn(name="id")
    private ArrayList<Node> nodes;

    public Network(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void removeNode(Node node) {
        this.nodes.remove(node);
    }
}

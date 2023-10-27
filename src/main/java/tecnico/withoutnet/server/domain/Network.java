package tecnico.withoutnet.server.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "networks")
public class Network {
    @Id
    private String name;

    @OneToMany(mappedBy = "network", cascade = CascadeType.ALL)
    private List<Node> nodes;

    public Network() {
        this.name = "";
        this.nodes = new ArrayList<>();
    }

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

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public void removeNode(Node node) {
        this.nodes.remove(node);
    }
}

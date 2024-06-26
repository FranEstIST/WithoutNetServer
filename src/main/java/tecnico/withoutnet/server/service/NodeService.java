package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Service
public interface NodeService {
    public List<Node> getAllNodes();
    public Node getNodeById(int id);
    public Node getNodeByNetworkNameAndCommonName(String networkName, String commonName);
    public List<Node> findNodesBySearchTerm(String searchTerm);
    public List<Node> findNodesByNetworkIdAndSearchTerm(int networkId, String searchTerm);
    public List<Node> findNodesByNetworkIdOrWithoutANetworkAndSearchTerm(int networkId, String searchTerm);
    public List<Node> getNodesWithoutANetwork();
    public Node addNode(Node node);
    public Node updateNode(Node node);
    public void renameNode(int nodeId, String newCommonName);
    public void deleteNode(int nodeId);
}

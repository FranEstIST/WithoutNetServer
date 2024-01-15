package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;

@Service
public interface NodeService {
    public Node getNodeById(int id);
    public Node getNodeByNetworkNameAndCommonName(String networkName, String commonName);
    public Node addNode(Node node);
    public void renameNode(int nodeId, String newCommonName);
    public void deleteNode(int nodeId);
}

package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;

@Service
public interface NodeService {
    public Node getNodeById(int id);
    public Node getNodeByNetworkNameAndCommonName(String networkName, String commonName);
    public void addNode(Node node);

    // TODO: This method may be unnecessary (it might be better to simply call the Node Class's
    // setCommonName method directly)
    public void renameNode(Node node, String newCommonName);

    public void deleteNode(Node node);
}

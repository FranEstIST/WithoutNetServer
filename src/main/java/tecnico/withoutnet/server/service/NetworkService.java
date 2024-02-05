package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Service
public interface NetworkService {
    Network getNetworkById(int networkId);
    Network getNetworkByName(String networkName);
    List<Network> findNetworksBySearchTerm(String searchTerm);
    List<Node> getAllNodesInNetwork(int networkId);
    Network addNetwork(Network network);
    void renameNetwork(int networkId, String newName);
    void addNodeToNetwork(int nodeId, int networkId);
    void removeNodeFromNetwork(int nodeId, int networkId);
    void deleteNetwork(int networkId);
}

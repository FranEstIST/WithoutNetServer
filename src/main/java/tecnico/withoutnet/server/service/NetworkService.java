package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

@Service
public interface NetworkService {
    Network getNetworkByName(String networkName);
    void addNetwork(Network network);
    void renameNetwork(Network network);
    void addNodeToNetwork(Node node, Network network);
    void deleteNetwork(Network network);
}

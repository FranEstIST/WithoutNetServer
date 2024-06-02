package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.repo.NetworkRepo;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NetworkServiceImpl implements NetworkService {
    @Autowired
    private NetworkRepo networkRepo;

    @Autowired
    private NodeRepo nodeRepo;

    @Override
    public Network getNetworkById(int networkId) {
        return networkRepo.getById(networkId);
    }

    @Override
    public Network getNetworkByName(String networkName) {
        return networkRepo.findByName(networkName);
    }

    @Override
    public List<Network> findNetworksBySearchTerm(String searchTerm) {
        List<Network> networks = new ArrayList<>();
        String searchPattern = "%"+ searchTerm.toLowerCase() + "%";

        try {
            Integer.parseInt(searchTerm);
            List<Network> networksWithMatchingId = networkRepo.findByIdPattern(searchPattern);
            networks.addAll(networksWithMatchingId);
        } catch (NumberFormatException e) {}

        List<Network> networksWithMatchingName = networkRepo.findByNamePattern(searchPattern);
        networks.addAll(networksWithMatchingName);

        return networks;
    }

    @Override
    public List<Node> getAllNodesInNetwork(int networkId) {
        Network network = networkRepo.getById(networkId);
        return network.getNodes();
    }

    @Override
    public Network addNetwork(Network network) {
        return networkRepo.save(network);
    }

    @Override
    public void renameNetwork(int networkId, String newName) {
        Network networkToBeRenamed = networkRepo.getById(networkId);
        networkToBeRenamed.setName(newName);
    }

    @Override
    public void addNodeToNetwork(int nodeId, int networkId) {
        Node node = nodeRepo.getById(nodeId);

        // TODO: Check if the node is actually removed
        // from the network in the db
        Network previousNetwork = node.getNetwork();
        previousNetwork.removeNode(node);

        Network network = networkRepo.getById(networkId);
        node.setNetwork(network);
        network.addNode(node);
    }

    @Override
    public void removeNodeFromNetwork(int nodeId, int networkId) {
        Node node = nodeRepo.getById(nodeId);

        // TODO: Check if the node is actually removed
        // from the network in the db
        Network network = networkRepo.getById(networkId);
        // TODO: Check if node is actually in the network

        network.removeNode(node);
        node.setNetwork(null);
    }

    @Override
    public void deleteNetwork(int networkId) {
        Network network = networkRepo.getById(networkId);
        networkRepo.delete(network);
    }
}

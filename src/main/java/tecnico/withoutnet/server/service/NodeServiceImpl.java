package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeRepo nodeRepo;

    public static Specification<Node> hasCommonName(String commonName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.<String>get("commonName"), commonName);
    }

    public static Specification<Node> isInNetworkWithNetworkName(String networkName) {
        return (root, query, criteriaBuilder) -> {
            Join<Network, Node> networksNode = root.join("networks");
            return criteriaBuilder.equal(networksNode.get("name"), networkName);
        };
    }

    @Override
    public Node getNodeById(int id) {
        return nodeRepo.findById(id);
    }

    @Override
    public Node getNodeByNetworkNameAndCommonName(String networkName, String commonName) {
        //List<Node> nodes = nodeRepo.findAll(hasCommonName(commonName).and(isInNetworkWithNetworkName(networkName)));
        //List<Node> nodes = nodeRepo.findNodeByNetworkNameAndCommonName(networkName, commonName);
        //return (nodes == null || nodes.isEmpty()) ? null : nodes.get(0);
        return null;
    }

    @Override
    public void addNode(Node node) {
        nodeRepo.save(node);
    }

    @Override
    public void renameNode(Node node, String newCommonName) {
        node.setCommonName(newCommonName);
    }

    @Override
    public void deleteNode(Node node) {
        nodeRepo.delete(node);
    }
}

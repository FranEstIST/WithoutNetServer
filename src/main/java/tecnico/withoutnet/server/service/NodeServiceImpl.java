package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.exceptions.DuplicateNodeNameException;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeRepo nodeRepo;

    public NodeServiceImpl(NodeRepo nodeRepo) {
        this.nodeRepo = nodeRepo;
    }

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
        Node node;

        try {
            node = nodeRepo.findById(id);
        } catch (JpaObjectRetrievalFailureException e) {
            // Add this non-existent to the server's node repo, with its common name and network as N/A
            node = new Node("N/A");
            addNode(node);
        }

        return node;
    }

    @Override
    public Node getNodeByNetworkNameAndCommonName(String networkName, String commonName) {
        //List<Node> nodes = nodeRepo.findAll(hasCommonName(commonName).and(isInNetworkWithNetworkName(networkName)));
        List<Node> nodes = nodeRepo.findNodeByNetworkNameAndCommonName(networkName, commonName);
        return (nodes == null || nodes.isEmpty()) ? null : nodes.get(0);
        //return null;
    }

    @Override
    public void addNode(Node node) throws DuplicateNodeNameException {
        if(nodeRepo.existsById(node.getId())) {
            throw new DuplicateNodeNameException("Node with name " + node.getCommonName() + " already exists");
        }
        nodeRepo.save(node);
    }

    @Override
    public void renameNode(int nodeId, String newCommonName) {
        Node node = getNodeById(nodeId);
        node.setCommonName(newCommonName);
    }

    @Override
    public void deleteNode(int nodeId) {
        Node node = nodeRepo.getById(nodeId);
        nodeRepo.delete(node);
    }
}

package tecnico.withoutnet.server.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class NodeRepoTest {
    @Autowired
    private NetworkRepo networkRepo;

    @Autowired
    private NodeRepo nodeRepo;

    @Test
    public void shouldSaveNode() {
        Network networkOne = new Network("networkOne");
        networkRepo.save(networkOne);
        Node expectedNode = new Node("nodeOne", networkOne);
        Node savedNode = nodeRepo.save(expectedNode);
        assertThat(expectedNode).usingRecursiveComparison().ignoringFields("id").isEqualTo(savedNode);
    }

    @Test
    public void shouldChangeNodesCommonName() {
        Network networkOne = new Network("networkOne");
        networkRepo.save(networkOne);
        Node nodeOne = new Node("nodeOne", networkOne);
        Node expectedSavedNode = nodeRepo.save(nodeOne);
        expectedSavedNode.setCommonName("nodeOnePrime");
        Node savedNode = nodeRepo.findById(expectedSavedNode.getId());
        assertThat(expectedSavedNode).usingRecursiveComparison().isEqualTo(savedNode);
    }

    @Test
    public void shouldDeleteNode() {
        Network network = new Network("networkOne");
        Network savedNetwork = networkRepo.save(network);
        Node node = new Node("nodeOne", network);
        Node savedNodeOne = nodeRepo.save(node);

        nodeRepo.delete(savedNodeOne);

        assertThatThrownBy(() -> {
            nodeRepo.getById(node.getId());
        }).isInstanceOf(JpaObjectRetrievalFailureException.class);

        Network savedNetworkPrime = networkRepo.getById(network.getName());

        assertThat(savedNetworkPrime).isEqualTo(savedNetwork);
    }
}

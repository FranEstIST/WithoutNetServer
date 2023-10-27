package tecnico.withoutnet.server.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class NetworkRepoTest {
    @Autowired
    private NetworkRepo networkRepo;

    @Autowired
    private NodeRepo nodeRepo;

    @Test
    public void shouldSaveNetworkWithNoNodes() {
        Network expectedNetwork = new Network("network1");
        Network savedNetwork = networkRepo.save(expectedNetwork);
        assertThat(expectedNetwork).usingRecursiveComparison().isEqualTo(savedNetwork);
    }

    @Test
    public void shouldSaveNetworkWithNodes() {
        Network expectedNetwork = new Network("networkOne");
        Network savedNetwork = networkRepo.save(expectedNetwork);

        Node nodeOne = new Node("nodeOne", savedNetwork);
        //expectedNetwork.addNode(nodeOne);

        Node savedNode = nodeRepo.save(nodeOne);
        savedNetwork.addNode(savedNode);

        savedNetwork = networkRepo.getById("networkOne");

        assertThat(savedNetwork.getNodes().get(0)).usingRecursiveComparison().isEqualTo(savedNode);
    }

    @Test
    public void shouldMoveNodeToOtherNetwork() {
        Network networkOne = new Network("networkOne");
        Network networkTwo = new Network("networkTwo");

        Network savedNetworkOne = networkRepo.save(networkOne);
        Network savedNetworkTwo = networkRepo.save(networkTwo);

        Node nodeOne = new Node("nodeTwo", savedNetworkOne);
        Node savedNodeOne = nodeRepo.save(nodeOne);

        savedNetworkOne.addNode(savedNodeOne);

        Network savedNetworkOnePrime = networkRepo.getById("networkOne");

        assertThat(savedNetworkOne).usingRecursiveComparison().isEqualTo(savedNetworkOnePrime);

        savedNodeOne.setNetwork(savedNetworkTwo);
        savedNetworkTwo.addNode(savedNodeOne);
        Network savedNetworkTwoPrime = networkRepo.findByName("networkTwo");

        assertThat(savedNetworkTwo).usingRecursiveComparison().isEqualTo(savedNetworkTwoPrime);

        savedNetworkOne.removeNode(nodeOne);
        savedNetworkOnePrime = networkRepo.getById("networkOne");

        assertThat(savedNetworkOnePrime).isEqualTo(savedNetworkOne);
    }

    @Test
    public void shouldDeleteNetworkWithoutNodes() {
        Network network = new Network("networkOne");
        Network savedNetwork = networkRepo.save(network);
        networkRepo.delete(savedNetwork);

        assertThatThrownBy(() -> {
            networkRepo.getById(network.getName());
        }).isInstanceOf(JpaObjectRetrievalFailureException.class);
    }

    @Test
    public void shouldDeleteNetworkWithNodes() {
        Network network = new Network("networkOne");
        Network savedNetwork = networkRepo.save(network);

        Node nodeOne = new Node("nodeOne", savedNetwork);
        Node savedNodeOne = nodeRepo.save(nodeOne);

        savedNetwork.addNode(savedNodeOne);

        networkRepo.delete(savedNetwork);

        assertThatThrownBy(() -> {
            networkRepo.getById(savedNetwork.getName());
        }).isInstanceOf(JpaObjectRetrievalFailureException.class);

        assertThatThrownBy(() -> {
            nodeRepo.getById(savedNodeOne.getId());
        }).isInstanceOf(JpaObjectRetrievalFailureException.class);
    }
}

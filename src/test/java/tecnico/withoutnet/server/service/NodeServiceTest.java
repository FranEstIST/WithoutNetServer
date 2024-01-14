package tecnico.withoutnet.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class NodeServiceTest {
    private NodeService nodeService;

    @Mock
    private NodeRepo nodeRepo;

    @Captor
    private ArgumentCaptor<Node> nodeArgumentCaptor;

    @BeforeEach
    public void setup() {
        nodeService = new NodeServiceImpl(nodeRepo);
    }

    @Test
    public void getExistingNodeById() {
        Node expectedNode = new Node("node");

        Mockito.when(nodeRepo.findById(1)).thenReturn(expectedNode);

        Node returnedNode = nodeService.getNodeById(1);

        assertThat(returnedNode).isEqualTo(expectedNode);
    }

    @Test
    public void getNonExistentNodeById() {
        Mockito.when(nodeRepo.findById(1)).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));

        Node returnedNode = nodeService.getNodeById(1);

        assertThat(returnedNode.getCommonName()).isEqualTo("N/A");
    }

    @Test
    public void getNodeByNetworkAndCommonName() {
        Network expectedNetwork = new Network("network");

        Node expectedNode = new Node("commonName", expectedNetwork);

        ArrayList<Node> expectedNodeList = new ArrayList<>();
        expectedNodeList.add(expectedNode);

        Mockito.when(nodeRepo.findNodeByNetworkNameAndCommonName("network", "commonName")).thenReturn(expectedNodeList);

        Node returnedNode = nodeService.getNodeByNetworkNameAndCommonName("network", "commonName");

        assertThat(returnedNode).isEqualTo(expectedNodeList.get(0));
    }

    @Test
    public void addNode() {
        Node newNode = new Node("node");

        Mockito.when(nodeRepo.save(newNode)).thenReturn(newNode);

        nodeService.addNode(newNode);

        Mockito.verify(nodeRepo, Mockito.times(1)).save(nodeArgumentCaptor.capture());

        assertThat(nodeArgumentCaptor.getValue()).isEqualTo(newNode);
    }

    @Test
    public void addExistingNode() {
        // TODO
        Node newNode = new Node("node");

        Mockito.when(nodeRepo.save(newNode)).thenReturn(newNode);

        nodeService.addNode(newNode);

        Mockito.verify(nodeRepo, Mockito.times(1)).save(nodeArgumentCaptor.capture());

        assertThat(nodeArgumentCaptor.getValue()).isEqualTo(newNode);
    }

    @Test
    public void deleteNode() {
        Node nodeToBeDeleted = new Node("node");

        nodeService.deleteNode(nodeToBeDeleted);

        Mockito.verify(nodeRepo, Mockito.times(1)).delete(nodeArgumentCaptor.capture());

        assertThat(nodeArgumentCaptor.getValue()).isEqualTo(nodeToBeDeleted);
    }

    @Test
    public void deleteNonExistentNode() {
        // TODO

        Node nodeToBeDeleted = new Node("node");

        nodeService.deleteNode(nodeToBeDeleted);

        Mockito.verify(nodeRepo, Mockito.times(1)).delete(nodeArgumentCaptor.capture());

        assertThat(nodeArgumentCaptor.getValue()).isEqualTo(nodeToBeDeleted);
    }

}
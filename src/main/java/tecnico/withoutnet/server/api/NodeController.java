package tecnico.withoutnet.server.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tecnico.withoutnet.server.StatusCodes;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.service.NetworkService;
import tecnico.withoutnet.server.service.NodeService;
import tecnico.withoutnet.server.utils.ControllerUtils;

import java.util.List;

@RestController
public class NodeController {
    @Autowired
    NodeService nodeService;

    @Autowired
    NetworkService networkService;

    @GetMapping("get-all-nodes")
    public String getAllNodes() {
        List<Node> nodesWithoutANetwork = nodeService.getAllNodes();

        JsonArray nodesJson = new JsonArray();

        for(Node node : nodesWithoutANetwork) {
            JsonObject nodeJson = ControllerUtils.getNodeJson(node);
            nodesJson.add(nodeJson);
        }

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("nodes", nodesJson);

        return response.toString();
    }

    @GetMapping("get-node-by-id/{nodeId}")
    public String getNodeById(@PathVariable int nodeId) {
        Node node = nodeService.getNodeById(nodeId);

        JsonObject nodeJson = ControllerUtils.getNodeJson(node);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("node", nodeJson);

        return response.toString();
    }

    @GetMapping("get-nodes-without-a-network")
    public String getNodesWithoutANetwork() {
        List<Node> nodesWithoutANetwork = nodeService.getNodesWithoutANetwork();

        JsonArray nodesJson = new JsonArray();

        for(Node node : nodesWithoutANetwork) {
            JsonObject nodeJson = ControllerUtils.getNodeJson(node);
            nodesJson.add(nodeJson);
        }

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("nodes", nodesJson);

        return response.toString();
    }

    @GetMapping("find-nodes-by-search-term/{searchTerm}")
    public String findNodesBySearchTerm(@PathVariable String searchTerm) {
        List<Node> nodes = nodeService.findNodesBySearchTerm(searchTerm);

        JsonArray nodesJson = new JsonArray();

        for(Node node : nodes) {
            JsonObject nodeJson = ControllerUtils.getNodeJson(node);
            nodesJson.add(nodeJson);
        }

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("nodes", nodesJson);

        return response.toString();
    }

    @GetMapping("find-nodes-by-network-id-and-search-term/{networkId}/{searchTerm}")
    public String findNodesByNetworkIdAndSearchTerm(@PathVariable int networkId, @PathVariable String searchTerm) {
        List<Node> nodes = nodeService.findNodesByNetworkIdAndSearchTerm(networkId, searchTerm);

        JsonArray nodesJson = new JsonArray();

        for(Node node : nodes) {
            JsonObject nodeJson = ControllerUtils.getNodeJson(node);
            nodesJson.add(nodeJson);
        }

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("nodes", nodesJson);

        return response.toString();
    }

    @PostMapping("add-node")
    public String addNode(@RequestBody AddNodeRequest addNodeRequest) {
        Node newNode;

        if(addNodeRequest.getNetworkId() == -1) {
            newNode = new Node(addNodeRequest.getCommonName());
        } else {
            Network network = networkService.getNetworkById(addNodeRequest.getNetworkId());
            newNode = new Node(addNodeRequest.getCommonName(), network);
        }

        Node savedNode = nodeService.addNode(newNode);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        JsonObject savedNodeJson = ControllerUtils.getNodeJson(savedNode);
        response.add("node", savedNodeJson);

        return response.toString();
    }

    @PostMapping("update-node")
    public String updateNode(@RequestBody UpdateNodeRequest updateNodeRequest) {
        Node node;

        if(updateNodeRequest.getNetworkId() == -1) {
            node = new Node(updateNodeRequest.getId(), updateNodeRequest.getCommonName());
        } else {
            Network network = networkService.getNetworkById(updateNodeRequest.getNetworkId());
            node = new Node(updateNodeRequest.getId(), updateNodeRequest.getCommonName(), network);
        }

        Node updatedNode = nodeService.updateNode(node);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        JsonObject savedNodeJson = ControllerUtils.getNodeJson(updatedNode);
        response.add("node", savedNodeJson);

        return response.toString();
    }

    @PostMapping("rename-node")
    public String renameNode(@RequestBody RenameNodeRequest renameNodeRequest) {
        nodeService.renameNode(renameNodeRequest.getNodeId(),
                renameNodeRequest.getNewCommonName());

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);

        return response.toString();
    }

    @PostMapping("delete-node/{nodeId}")
    public String deleteNode(@PathVariable int nodeId) {
        nodeService.deleteNode(nodeId);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);

        return response.toString();
    }
}

class AddNodeRequest {
    private final String commonName;

    private final int networkId;

    public AddNodeRequest(String commonName, int networkId) {
        this.commonName = commonName;
        this.networkId = networkId;
    }

    public String getCommonName() {
        return commonName;
    }

    public int getNetworkId() {
        return networkId;
    }
}

class UpdateNodeRequest {
    private final int id;

    private final String commonName;

    private final int networkId;

    public UpdateNodeRequest(int id, String commonName, int networkId) {
        this.id = id;
        this.commonName = commonName;
        this.networkId = networkId;
    }

    public int getId() {
        return id;
    }

    public String getCommonName() {
        return commonName;
    }

    public int getNetworkId() {
        return networkId;
    }
}

class RenameNodeRequest {
    private final int nodeId;
    private final String newCommonName;

    public RenameNodeRequest(int nodeId, String newCommonName) {
        this.nodeId = nodeId;
        this.newCommonName = newCommonName;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getNewCommonName() {
        return newCommonName;
    }
}

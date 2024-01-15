package tecnico.withoutnet.server.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tecnico.withoutnet.server.StatusCodes;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.service.NodeService;
import tecnico.withoutnet.server.utils.ControllerUtils;

import java.util.List;

@Controller
public class NodeController {
    @Autowired
    NodeService nodeService;

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

    @PostMapping("add-node")
    public String addNode(@RequestBody AddNodeRequest addNodeRequest) {
        Node newNode = new Node(addNodeRequest.getCommonName());

        Node savedNode = nodeService.addNode(newNode);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);

        JsonObject savedNodeJson = ControllerUtils.getNodeJson(savedNode);

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

    public AddNodeRequest(String commonName) {
        this.commonName = commonName;
    }

    public String getCommonName() {
        return commonName;
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

package tecnico.withoutnet.server.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tecnico.withoutnet.server.StatusCodes;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.service.NetworkService;
import tecnico.withoutnet.server.service.NodeService;
import tecnico.withoutnet.server.utils.ControllerUtils;

import java.util.List;

import static tecnico.withoutnet.server.utils.ControllerUtils.createStatusJson;
import static tecnico.withoutnet.server.utils.ControllerUtils.getNetworkJson;

@RestController
public class NetworkController {
    @Autowired
    private NetworkService networkService;

    @Autowired
    private NodeService nodeService;

    @GetMapping("get-network-by-id/{networkId}")
    public String getNetworkById(@PathVariable int networkId) {
        Network network = networkService.getNetworkById(networkId);
        JsonObject networkJson = ControllerUtils.getNetworkJson(network);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("network", networkJson);

        return response.toString();
    }

    @GetMapping("get-network-by-name/{name}")
    public String getNetworkByName(@PathVariable String name) {
        Network network = networkService.getNetworkByName(name);
        JsonObject networkJson = ControllerUtils.getNetworkJson(network);

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("network", networkJson);

        return response.toString();
    }

    @GetMapping("find-networks-by-search-term/{searchTerm}")
    public String findNetworksByTerm(@PathVariable String searchTerm) {
        List<Network> networks = networkService.findNetworksBySearchTerm(searchTerm);

        JsonArray networksJsonArray = new JsonArray();

        for(Network network : networks) {
            JsonObject networkJson = ControllerUtils.getNetworkJson(network);
            networksJsonArray.add(networkJson);
        }

        JsonObject response = ControllerUtils.createStatusJson(StatusCodes.OK);
        response.add("networks", networksJsonArray);

        return response.toString();
    }

    @PostMapping("add-network")
    public String addNetwork(@RequestBody AddNetworkRequest addNetworkRequest) {
        Network newNetwork = new Network(addNetworkRequest.getName());
        Network savedNetwork = networkService.addNetwork(newNetwork);

        JsonObject response = createStatusJson(StatusCodes.OK);
        JsonObject savedNetworkJson = getNetworkJson(savedNetwork);
        response.add("network", savedNetworkJson);

        return response.toString();
    }

    @PostMapping("rename-network")
    public String renameNetwork(@RequestBody RenameNetworkRequest renameNetworkRequest) {
        Network newNetwork = new Network(renameNetworkRequest.getNewName());
        networkService.addNetwork(newNetwork);
        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @PostMapping("delete-network")
    public String deleteNetwork(@RequestBody DeleteNetworkRequest deleteNetworkRequest) {
        networkService.deleteNetwork(deleteNetworkRequest.getNetworkId());
        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @PostMapping("add-node-to-network")
    public String addNodeToNetwork(@RequestBody AddNodeToNetworkRequest addNodeToNetworkRequest) {
        networkService.addNodeToNetwork(addNodeToNetworkRequest.getNodeId(),
                addNodeToNetworkRequest.getNetworkId());
        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @PostMapping("remove-node-from-network")
    public String removeNodeFromNetwork(@RequestBody RemoveNodeFromNetworkRequest removeNodeFromNetworkRequest) {
        networkService.removeNodeFromNetwork(removeNodeFromNetworkRequest.getNodeId(),
                removeNodeFromNetworkRequest.getNetworkId());
        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }
}

class AddNetworkRequest {
    private String name;

    public AddNetworkRequest() {
    }

    public AddNetworkRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class RenameNetworkRequest {
    private final int networkId;
    private final String newName;

    public RenameNetworkRequest(int networkId, String newName) {
        this.networkId = networkId;
        this.newName = newName;
    }

    public int getNetworkId() {
        return networkId;
    }

    public String getNewName() {
        return newName;
    }
}

class DeleteNetworkRequest {
    private final int networkId;

    public DeleteNetworkRequest(int networkId) {
        this.networkId = networkId;
    }

    public int getNetworkId() {
        return networkId;
    }
}

class AddNodeToNetworkRequest {
    private final int nodeId;
    private final int networkId;

    public AddNodeToNetworkRequest(int nodeId, int networkId) {
        this.nodeId = nodeId;
        this.networkId = networkId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getNetworkId() {
        return networkId;
    }
}

class RemoveNodeFromNetworkRequest {
    private final int nodeId;
    private final int networkId;

    public RemoveNodeFromNetworkRequest(int nodeId, int networkId) {
        this.nodeId = nodeId;
        this.networkId = networkId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getNetworkId() {
        return networkId;
    }
}

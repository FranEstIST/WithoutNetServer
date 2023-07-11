package tecnico.withoutnet.server.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tecnico.withoutnet.server.StatusCodes;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.domain.Update;
import tecnico.withoutnet.server.service.NodeService;
import tecnico.withoutnet.server.service.UpdateService;

@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @Autowired
    private NodeService nodeService;

    @PostMapping("add-update")
    public String addUpdate(@RequestBody Update update) {
        updateService.addUpdate(update);
        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @GetMapping("get-most-recent-update-by-node-id/{nodeId}")
    public String getMostRecentUpdateByNode(@PathVariable String nodeId) {
        Node node = nodeService.getNodeById(nodeId);

        if(node == null) {
            JsonObject response = createStatusJson(StatusCodes.NO_UPDATE_FOUND_FOR_NODE);
            return response.toString();
        }

        Update update = updateService.getMostRecentUpdateByNode(node);

        JsonObject response = createStatusJson(StatusCodes.OK);
        response.add("update", getUpdateJson(update));

        return response.toString();
    }

    private JsonObject createStatusJson(int status) {
        JsonObject statusJson = JsonParser.parseString("{}").getAsJsonObject();
        statusJson.addProperty("status", status);
        return statusJson;
    }

    private JsonObject getUpdateJson(Update update) {
        JsonObject updateJson = JsonParser.parseString("{}").getAsJsonObject();
        updateJson.addProperty("timestamp", update.getTimestamp());
        updateJson.add("sender", getNodeJson(update.getSender()));
        updateJson.addProperty("reading", update.getReading());
        return updateJson;
    }

    private JsonObject getNodeJson(Node node) {
        JsonObject nodeJson = JsonParser.parseString("{}").getAsJsonObject();
        nodeJson.addProperty("id", node.getUuid());
        nodeJson.addProperty("common-name", node.getCommonName());
        nodeJson.addProperty("reading-type", node.getReadingType());
        return nodeJson;
    }
}

/*class MarshalledUpdate {

}*/

/*class AddUpdateRequest {
    private final String timestamp;
    private final
    private final String reading;
}*/
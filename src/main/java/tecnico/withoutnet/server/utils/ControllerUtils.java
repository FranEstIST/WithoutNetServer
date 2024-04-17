package tecnico.withoutnet.server.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

public class ControllerUtils {
    public static JsonObject createStatusJson(int status) {
        JsonObject statusJson = JsonParser.parseString("{}").getAsJsonObject();
        statusJson.addProperty("status", status);
        return statusJson;
    }

    public static JsonObject getNodeJson(Node node) {
        JsonObject nodeJson = JsonParser.parseString("{}").getAsJsonObject();

        nodeJson.addProperty("id", node.getId());
        nodeJson.addProperty("common-name", node.getCommonName());
        // TODO: How are nodes without a network handled?
        if(node.getNetwork() != null) {
            nodeJson.addProperty("network-id", node.getNetwork().getId());
            nodeJson.addProperty("network-name", node.getNetwork().getName());
        } else {
            nodeJson.addProperty("network-id", -1);
            nodeJson.addProperty("network-name", "");
        }

        return nodeJson;
    }

    public static JsonObject getNetworkJson(Network network) {
        JsonObject networkJson = JsonParser.parseString("{}").getAsJsonObject();
        networkJson.addProperty("id", network.getId());
        networkJson.addProperty("name", network.getName());

        JsonArray nodesJson = new JsonArray();

        for(Node node : network.getNodes()) {
            JsonElement nodeJson = getNodeJson(node);
            nodesJson.add(nodeJson);
        }

        networkJson.add("nodes", nodesJson);

        return networkJson;
    }
}

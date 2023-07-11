package tecnico.withoutnet.server.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tecnico.withoutnet.server.StatusCodes;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.domain.Message;
import tecnico.withoutnet.server.service.NodeService;
import tecnico.withoutnet.server.service.MessageService;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("add-message")
    public String addMessage(@RequestBody AddMessageRequest addMessageRequest) {
        long localId = addMessageRequest.getLocalId();
        int messageType = addMessageRequest.getMessageType();
        long timestamp = addMessageRequest.getTimestamp();
        String sender = addMessageRequest.getSender();
        String receiver = addMessageRequest.getReceiver();
        String content = addMessageRequest.getContent();

        Message receivedMessage = new Message(localId, messageType, timestamp, sender, receiver, content);

        messageService.addMessage(receivedMessage);
        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @PostMapping("get-messages-by-receiver/{receiver}")
    public String getMostRecentMessageByReceiver(@PathVariable String receiver) {
        /*if(receiver not valid) {
            JsonObject response = createStatusJson(StatusCodes.NO_UPDATE_FOUND_FOR_NODE);
            return response.toString();
        }*/

        List<Message> messages = messageService.getMessageByReceiver(receiver);

        if(messages == null || messages.isEmpty()) {
            JsonObject response = createStatusJson(StatusCodes.NO_UPDATE_FOUND_FOR_NODE);
            return response.toString();
        }

        JsonObject response = createStatusJson(StatusCodes.OK);
        JsonArray messagesJsonArray = new JsonArray();
        for(Message message : messages) {
            messagesJsonArray.add(getMessageJson(message));
        }
        response.add("messages", messagesJsonArray);

        return response.toString();
    }

    @PostMapping("get-all-messages")
    public String getAllMessages() {
        /*if(receiver not valid) {
            JsonObject response = createStatusJson(StatusCodes.NO_UPDATE_FOUND_FOR_NODE);
            return response.toString();
        }*/

        List<Message> messages = messageService.getAllMessages();

        if(messages == null || messages.isEmpty()) {
            JsonObject response = createStatusJson(StatusCodes.NO_UPDATE_FOUND_FOR_NODE);
            return response.toString();
        }

        JsonObject response = createStatusJson(StatusCodes.OK);
        JsonArray messagesJsonArray = new JsonArray();
        for(Message message : messages) {
            messagesJsonArray.add(getMessageJson(message));
        }
        response.add("messages", messagesJsonArray);

        return response.toString();
    }

    private JsonObject createStatusJson(int status) {
        JsonObject statusJson = JsonParser.parseString("{}").getAsJsonObject();
        statusJson.addProperty("status", status);
        return statusJson;
    }

    private JsonObject getMessageJson(Message message) {
        JsonObject messageJson = JsonParser.parseString("{}").getAsJsonObject();
        messageJson.addProperty("id", message.getLocalId());
        messageJson.addProperty("messageType", message.getMessageType());
        messageJson.addProperty("timestamp", message.getTimestamp());
        messageJson.addProperty("sender", message.getSender());
        messageJson.addProperty("receiver", message.getReceiver());
        messageJson.addProperty("content", message.getContent());
        return messageJson;
    }

    private JsonObject getNodeJson(Node node) {
        JsonObject nodeJson = JsonParser.parseString("{}").getAsJsonObject();
        nodeJson.addProperty("id", node.getUuid());
        nodeJson.addProperty("common-name", node.getCommonName());
        nodeJson.addProperty("reading-type", node.getReadingType());
        return nodeJson;
    }
}

class AddMessageRequest {
    private final long localId;
    private final int messageType;
    private final long timestamp;
    private final String sender;
    private final String receiver;
    private final String content;

    public AddMessageRequest(long localId, int messageType, long timestamp, String sender, String receiver, String content) {
        this.localId = localId;
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public long getLocalId() {
        return localId;
    }

    public int getMessageType() {
        return messageType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }
}
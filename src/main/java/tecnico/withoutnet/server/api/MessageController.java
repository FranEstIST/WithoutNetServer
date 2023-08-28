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

    @PostMapping("add-message-batch")
    public String addMessageBatch(@RequestBody AddMessageBatchRequest addMessageBatchRequest) {
        List<AddMessageRequest> messageBatch = addMessageBatchRequest.getMessageBatch();

        for(AddMessageRequest addMessageRequest : messageBatch) {
            int length = addMessageRequest.getLength();
            long timestamp = addMessageRequest.getTimestamp();
            int messageType = addMessageRequest.getMessageType();
            int sender = addMessageRequest.getSender();
            int receiver = addMessageRequest.getReceiver();
            String payload = addMessageRequest.getPayload();

            Message receivedMessage = new Message((short) length, timestamp, messageType, sender, receiver, payload);

            messageService.addMessage(receivedMessage);
        }

        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @PostMapping("add-message")
    public String addMessage(@RequestBody AddMessageRequest addMessageRequest) {
        int length = addMessageRequest.getLength();
        long timestamp = addMessageRequest.getTimestamp();
        int messageType = addMessageRequest.getMessageType();
        int sender = addMessageRequest.getSender();
        int receiver = addMessageRequest.getReceiver();
        String payload = addMessageRequest.getPayload();

        Message receivedMessage = new Message((short) length, timestamp, messageType, sender, receiver, payload);

        messageService.addMessage(receivedMessage);

        JsonObject response = createStatusJson(StatusCodes.OK);
        return response.toString();
    }

    @PostMapping("get-messages-by-receiver/{receiver}")
    public String getMostRecentMessageByReceiver(@PathVariable int receiver) {
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
        messageJson.addProperty("length", message.getLength());
        messageJson.addProperty("timestamp", message.getTimestamp());
        messageJson.addProperty("messageType", message.getMessageType());
        messageJson.addProperty("sender", message.getSender());
        messageJson.addProperty("receiver", message.getReceiver());
        messageJson.addProperty("payload", message.getPayload());
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

class AddMessageBatchRequest {
    private final List<AddMessageRequest> messageBatch;

    public AddMessageBatchRequest(List<AddMessageRequest> messageBatch) {
        this.messageBatch = messageBatch;
    }

    public List<AddMessageRequest> getMessageBatch() {
        return messageBatch;
    }
}

class AddMessageRequest {
    private final int length;
    private final long timestamp;
    private final int messageType;
    private final int sender;
    private final int receiver;
    private final String payload;

    public AddMessageRequest(int length, long timestamp, int messageType, int sender, int receiver, String payload) {
        this.length = length;
        this.timestamp = timestamp;
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
    }

    public int getLength() {
        return length;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String getPayload() {
        return payload;
    }
}
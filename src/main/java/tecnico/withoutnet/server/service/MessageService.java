package tecnico.withoutnet.server.service;

import tecnico.withoutnet.server.domain.Message;

import java.util.List;

public interface MessageService {
    void addMessage(Message message);
    List<Message> getMessageByReceiver(int receiver);
    List<Message> getMessagesBySenderAndReceiver(int senderId, int receiverId);
    List<Message> getMessagesBySenderAndReceiverAfterTimestamp(int senderId, int receiverId, long timestamp);
    List<Message> getAllMessages();
    void removeMessage(int senderId, int receiverID, long timestamp);
    void deleteAllMessages();
}

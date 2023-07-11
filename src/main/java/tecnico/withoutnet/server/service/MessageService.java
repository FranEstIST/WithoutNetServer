package tecnico.withoutnet.server.service;

import tecnico.withoutnet.server.domain.Message;

import java.util.List;

public interface MessageService {
    void addMessage(Message message);
    List<Message> getMessageByReceiver(String receiver);
    List<Message> getAllMessages();
}

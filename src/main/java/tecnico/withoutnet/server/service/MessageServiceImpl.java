package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Message;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.exceptions.MessageNotFoundException;
import tecnico.withoutnet.server.exceptions.NodeNotFoundException;
import tecnico.withoutnet.server.repo.MessageRepo;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private NodeRepo nodeRepo;

    @Override
    public void addMessage(Message message) {
        if(messageRepo.existsByTimestampAndMessageTypeAndSenderAndReceiverAndPayload(
                message.getTimestamp(),
                message.getMessageType(),
                message.getSender(),
                message.getReceiver(),
                message.getPayload())) {
            return;
        }

        try {
            messageRepo.save(message);
        } catch(org.hibernate.exception.ConstraintViolationException e) {
            // TODO: This exception is thrown when a duplicate update is sent to the server (not
            // necessarily an error), so it should be handled in a way that doesn't interfere
            // with the program's execution
        }

    }

    @Override
    public List<Message> getMessageByReceiver(int receiverId) {
        Node receiver = nodeRepo.findById(receiverId);

        if(receiver == null) {
            throw new NodeNotFoundException("No node found with id: "+ receiverId);
        }

        return messageRepo.findByReceiver(receiver);
    }

    @Override
    public List<Message> getMessagesBySenderAndReceiver(int senderId, int receiverId) {
        Node sender = nodeRepo.findById(senderId);

        if(sender == null) {
            throw new NodeNotFoundException("No node found with id: "+ senderId);
        }

        Node receiver = nodeRepo.findById(receiverId);

        if(receiver == null) {
            throw new NodeNotFoundException("No node found with id: "+ receiverId);
        }

        return messageRepo.findBySenderAndReceiver(sender, receiver);
    }

    @Override
    public List<Message> getMessagesBySenderAndReceiverAfterTimestamp(int senderId, int receiverId, long timestamp) {
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    @Override
    public void removeMessage(int senderId, int receiverId, long timestamp) throws NodeNotFoundException , MessageNotFoundException{
        Node sender = nodeRepo.findById(senderId);

        if(sender == null) {
            throw new NodeNotFoundException("No node found with id: "+ senderId);
        }

        Node receiver = nodeRepo.findById(receiverId);

        if(receiver == null) {
            throw new NodeNotFoundException("No node found with id: "+ receiverId);
        }

        Message message = messageRepo.findBySenderAndReceiverAndTimestamp(sender, receiver, timestamp);

        if(message == null) {
            throw new MessageNotFoundException("Message with senderId "
                    + senderId
                    + ", receiverId "
                    + receiverId
                    + " and timestamp "
                    + timestamp
                    + " was not found");
        }

        messageRepo.delete(message);
    }

    @Override
    public void deleteAllMessages() {
        messageRepo.deleteAll();
    }
}

package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Message;
import tecnico.withoutnet.server.repo.MessageRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;

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
    public List<Message> getMessageByReceiver(int receiver) {
        return messageRepo.findByReceiver(receiver);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    @Override
    public void deleteAllMessages() {
        messageRepo.deleteAll();
    }
}

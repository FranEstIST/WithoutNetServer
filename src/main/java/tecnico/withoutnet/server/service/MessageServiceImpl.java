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
        messageRepo.save(message);
    }

    @Override
    public List<Message> getMessageByReceiver(String receiver) {
        return messageRepo.findByReceiver(receiver);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }
}
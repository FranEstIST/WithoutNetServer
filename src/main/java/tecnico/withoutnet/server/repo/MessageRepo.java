package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Message;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(String receiver);
    List<Message> findByLocalIdAndMessageTypeAndTimestampAndSenderAndReceiverAndContent(long localId, int messageType, long timestamp, String sender, String Receiver, String content);
    boolean existsByLocalIdAndMessageTypeAndTimestampAndSenderAndReceiverAndContent(long localId, int messageType, long timestamp, String sender, String Receiver, String content);
}

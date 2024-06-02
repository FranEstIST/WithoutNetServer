package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Message;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(Node receiver);
    List<Message> findBySenderAndReceiver(Node sender, Node receiver);
    //List<Message> findByLocalIdAndMessageTypeAndTimestampAndSenderAndReceiverAndContent(long localId, int messageType, long timestamp, String sender, String Receiver, String content);
    Message findBySenderAndReceiverAndTimestamp(Node sender, Node receiver, long timestamp);
    boolean existsByTimestampAndMessageTypeAndSenderAndReceiverAndPayload(long timestamp, int messageType, Node sender, Node receiver, String payload);
}

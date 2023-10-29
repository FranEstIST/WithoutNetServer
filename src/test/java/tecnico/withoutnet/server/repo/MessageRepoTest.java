package tecnico.withoutnet.server.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import tecnico.withoutnet.server.domain.Message;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class MessageRepoTest {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private NetworkRepo networkRepo;

    @Autowired
    private NodeRepo nodeRepo;

    @Test
    public void shouldSaveMessage() {
        Node sender = new Node("sender");
        Node savedSender = nodeRepo.save(sender);

        Node receiver = new Node("receiver");
        Node savedReceiver = nodeRepo.save(receiver);

        Message expectedMessage = new Message((short) 1, 1L, 0, sender, receiver, "abc");

        Message savedMessage = messageRepo.save(expectedMessage);

        assertThat(expectedMessage).usingRecursiveComparison().ignoringFields("id").isEqualTo(savedMessage);
    }

    @Test
    public void shouldDeleteMessage() {
        Node sender = new Node("sender");
        Node savedSender = nodeRepo.save(sender);

        Node receiver = new Node("receiver");
        Node savedReceiver = nodeRepo.save(receiver);

        Message expectedMessage = new Message((short) 1, 1L, 0, sender, receiver, "abc");

        Message savedMessage = messageRepo.save(expectedMessage);

        assertThat(expectedMessage).usingRecursiveComparison().ignoringFields("id").isEqualTo(savedMessage);

        messageRepo.deleteById(savedMessage.getId());

        assertThatThrownBy(() -> {
            messageRepo.getById(savedMessage.getId());
        }).isInstanceOf(JpaObjectRetrievalFailureException.class);
    }
}

package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.domain.Update;

import java.util.List;

@Repository
public interface UpdateRepo extends JpaRepository<Update, Long> {
    List<Update> findBySender(Node node);
}

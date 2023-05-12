package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Node;

@Repository
public interface NodeRepo extends JpaRepository<Node, Long> {
    Node findById(String id);
}

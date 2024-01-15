package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

@Repository
public interface NetworkRepo extends JpaRepository<Network, Integer> {
    Network findByName(String name);
}

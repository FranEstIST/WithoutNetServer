package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Repository
public interface NodeRepo extends JpaRepository<Node, Integer> {
    Node findById(int id);
    @Query("SELECT * FROM networks JOIN nodes WHERE networks.name = ?1 AND nodes.commonName = ?2")
    List<Node> findNodeByNetworkNameAndCommonName(String networkName, String commonName);
}

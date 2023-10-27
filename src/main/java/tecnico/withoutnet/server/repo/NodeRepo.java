package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Repository
public interface NodeRepo extends JpaRepository<Node, Integer> {
    Node findById(int id);

    @Query("SELECT nds FROM Network nws JOIN Node nds WHERE nws.name = :networkName AND nds.commonName = :commonName")
    List<Node> findNodeByNetworkNameAndCommonName(@Param("networkName") String networkName, @Param("commonName") String commonName);
}

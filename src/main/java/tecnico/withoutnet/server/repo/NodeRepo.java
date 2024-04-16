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

    @Query("SELECT nd FROM Network nw INNER JOIN Node nd ON nw.name = :networkName AND nd.commonName = :commonName")
    List<Node> findNodeByNetworkNameAndCommonName(@Param("networkName") String networkName, @Param("commonName") String commonName);

    @Query("SELECT nd FROM Node nd WHERE nd.network IS NULL")
    List<Node> findNodesWithoutANetwork();

    @Query("SELECT nd FROM Node nd WHERE nd.network.id = :networkId")
    List<Node> findNodeByNetworkId(@Param("networkId") int networkId);

    @Query("SELECT nd FROM Node nd WHERE nd.network.id = :networkId AND nd.commonName LIKE :commonNamePattern")
    List<Node> findNodeByNetworkIdAndCommonNamePattern(@Param("networkId") int networkId, @Param("commonNamePattern") String commonNamePattern);

    @Query("SELECT nd FROM Node nd WHERE nd.network IS NULL AND nd.commonName LIKE :commonNamePattern")
    List<Node> findNodeWithoutANetworkByCommonNamePattern(@Param("commonNamePattern") String commonNamePattern);

    @Query("SELECT nd FROM Node nd WHERE nd.network.id = :networkId AND cast(nd.id as text) LIKE :idPattern")
    List<Node> findNodeByNetworkIdAndIdPattern(@Param("networkId") int networkId, @Param("idPattern") String idPattern);

    @Query("SELECT nd FROM Node nd WHERE nd.network IS NULL AND cast(nd.id as text) LIKE :idPattern")
    List<Node> findNodeWithoutANetworkByIdPattern(@Param("idPattern") String idPattern);

    @Query("SELECT nd FROM Node nd WHERE nd.network.id = :networkId AND nd.id = :nodeId")
    List<Node> findNodeByNetworkIdAndId(@Param("networkId") int networkId, @Param("nodeId") int nodeId);

    @Query("SELECT nd FROM Node nd WHERE nd.network IS NULL AND nd.id = :nodeId")
    List<Node> findNodeWithoutANetworkById(@Param("nodeId") int nodeId);
}

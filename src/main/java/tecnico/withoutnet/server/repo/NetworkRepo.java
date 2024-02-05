package tecnico.withoutnet.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;

import java.util.List;

@Repository
public interface NetworkRepo extends JpaRepository<Network, Integer> {
    Network findByName(String name);

    @Query("SELECT nw FROM Network nw WHERE nw.name LIKE :namePattern")
    List<Network> findByNamePattern(@Param("namePattern") String namePattern);

    @Query("SELECT nw FROM Network nw WHERE nw.id LIKE :idPattern")
    List<Network> findByIdPattern(@Param("idPattern") String idPattern);
}

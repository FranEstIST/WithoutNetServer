package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;

@Service
public interface NodeService {
    public Node getNodeById(String id);
}

package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.transaction.Transactional;

@Service
@Transactional
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeRepo nodeRepo;

    @Override
    public Node getNodeById(String id) {
        return nodeRepo.findByUuid(id);
    }
}

package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.domain.Update;

import java.util.List;

@Service
public interface UpdateService {
    void addUpdate(Update update);
    List<Update> getUpdatesByNode(Node node);
    Update getMostRecentUpdateByNode(Node node);
}

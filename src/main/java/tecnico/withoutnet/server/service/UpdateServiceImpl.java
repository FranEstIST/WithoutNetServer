package tecnico.withoutnet.server.service;

import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.domain.Update;

import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Override
    public void addUpdate(Update update) {

    }

    @Override
    public List<Update> getUpdatesByNode(Node node) {
        return null;
    }

    @Override
    public Update getMostRecentUpdateByNode(Node node) {
        return null;
    }
}

package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.domain.Update;
import tecnico.withoutnet.server.repo.UpdateRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private UpdateRepo updateRepo;

    @Override
    public void addUpdate(Update update) {
        updateRepo.save(update);
    }

    @Override
    public List<Update> getUpdatesByNode(Node node) {
        return updateRepo.findBySender(node);
    }

    @Override
    public Update getMostRecentUpdateByNode(Node node) {
        List<Update> updates = updateRepo.findBySender(node);

        if(updates == null) {
            return null;
        }

        return updates.get(updates.size() - 1);
    }
}

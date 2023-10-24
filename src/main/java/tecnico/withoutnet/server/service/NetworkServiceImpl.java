package tecnico.withoutnet.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tecnico.withoutnet.server.domain.Network;
import tecnico.withoutnet.server.domain.Node;
import tecnico.withoutnet.server.repo.NetworkRepo;
import tecnico.withoutnet.server.repo.NodeRepo;

import javax.transaction.Transactional;

@Service
@Transactional
public class NetworkServiceImpl implements NetworkService {
    @Autowired
    private NetworkRepo networkRepo;

    @Override
    public Network getNetworkByName(String networkName) {
        return networkRepo.findByName(networkName);
    }

    @Override
    public void addNetwork(Network network) {
        networkRepo.save(network);
    }

    @Override
    public void renameNetwork(Network network) {

    }

    @Override
    public void addNodeToNetwork(Node node, Network network) {

    }

    @Override
    public void deleteNetwork(Network network) {
        networkRepo.delete(network);
    }
}

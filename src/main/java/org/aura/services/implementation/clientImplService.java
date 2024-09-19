package org.aura.services.implementation;

import org.aura.models.Client;
import org.aura.repository.implementation.clientRepoImpl;
import org.aura.services.interfaces.clientInterfaceServ;

import java.util.List;
import java.util.Optional;

import static org.aura.utils.LoggerUtils.logInfo;


public class clientImplService implements clientInterfaceServ {

    private clientRepoImpl clientRepo;

    public clientImplService(clientRepoImpl clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public List<Client> getAllClients() {
        return null;
    }

    @Override
    public Client getClient(int id) {
        return Optional.ofNullable(clientRepo.findClientById(id))
                .orElseGet(() -> {
                    logInfo("Client non trouvé");
                    return null;
                });
    }

    @Override
    public Client getClientByName(String name) {
        return Optional.ofNullable(clientRepo.findClientByName(name))
                .orElseGet(() -> {
                    logInfo("Client non trouvé");
                    return null;
                });
    }

    @Override
    public void createClient(Client client) {
        clientRepo.addClient(client);
    }
}

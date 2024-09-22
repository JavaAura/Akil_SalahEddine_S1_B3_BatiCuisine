package org.aura.services.implementation;

import org.aura.models.Client;
import org.aura.repository.implementation.clientRepoImpl;
import org.aura.services.interfaces.clientInterfaceServ;

import java.util.List;
import java.util.Optional;

import static org.aura.utils.LoggerUtils.logInfo;
import static org.aura.utils.LoggerUtils.logWarn;


public class clientImplServ implements clientInterfaceServ {

    private final clientRepoImpl clientRepo;

    public clientImplServ() {
        this.clientRepo = new clientRepoImpl();
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
                    logInfo("Aucun client trouvé avec ce nom. Veuillez réessayer.");
                    return null;
                });
    }

    @Override
    public Client createClient(Client client) {
        int clientId = clientRepo.addClient(client);
        if (clientId > 0) {
            client.setId(clientId);
            return client;
        }
        return null;
    }
}

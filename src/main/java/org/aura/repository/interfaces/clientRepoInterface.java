package org.aura.repository.interfaces;

import org.aura.models.Client;

public interface clientRepoInterface {
    Client findClientById(int id);
    Client findClientByName(String name);
    int addClient(Client client);
}

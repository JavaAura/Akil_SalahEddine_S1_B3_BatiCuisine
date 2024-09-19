package org.aura.repository.interfaces;

import org.aura.models.Client;

import java.util.List;

public interface clientRepoInterface {
    List<Client> findAllClients();
    Client findClientById(int id);
    Client findClientByName(String name);
    void addClient(Client client);
}

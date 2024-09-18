package org.aura.services.interfaces;

import org.aura.models.Client;

import java.util.List;

public interface clientInterfaceServ {
    List<Client> getAllClients();
    Client getClient(int id);
    void createClient(Client client);
}
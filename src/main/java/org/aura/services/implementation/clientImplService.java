package org.aura.services.implementation;

import org.aura.models.Client;
import org.aura.services.interfaces.clientInterfaceServ;

import java.util.List;

public class clientImplService implements clientInterfaceServ {

    public clientImplService() {
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }

    @Override
    public Client getClient(int id) {
        return null;
    }

    @Override
    public void createClient(Client client) {

    }
}

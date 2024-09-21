package org.aura.services.implementation;

import org.aura.models.Client;
import org.aura.models.Projet;
import org.aura.repository.implementation.projectRepoImpl;
import org.aura.services.interfaces.projetInterfaceServ;

import java.util.List;

public class projetImplServ implements projetInterfaceServ {

    private final projectRepoImpl projectRepoImpl;

    public projetImplServ() {
        this.projectRepoImpl = new projectRepoImpl();
    }

    @Override
    public List<Projet> getAllProjects() {
        return List.of();
    }

    @Override
    public Projet getProject(int id) {
        return null;
    }

    @Override
    public int createProject(Projet projet, Client client) {
        return projectRepoImpl.addProject(projet,client.getId());
    }
}

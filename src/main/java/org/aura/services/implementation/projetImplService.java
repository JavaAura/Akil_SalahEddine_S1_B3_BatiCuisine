package org.aura.services.implementation;

import org.aura.models.Projet;
import org.aura.repository.implementation.projectRepoImpl;
import org.aura.repository.interfaces.projectRepoInterface;
import org.aura.services.interfaces.projetInterfaceServ;

import java.util.List;

public class projetImplService implements projetInterfaceServ {

    private final projectRepoImpl projectRepoImpl;

    public projetImplService() {
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
    public void createProject(Projet projet) {
        projectRepoImpl.addProject(projet);
    }
}

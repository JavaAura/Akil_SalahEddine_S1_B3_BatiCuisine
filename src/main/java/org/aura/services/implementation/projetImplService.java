package org.aura.services.implementation;

import org.aura.models.Projet;
import org.aura.repository.interfaces.projectRepoInterface;
import org.aura.services.interfaces.projetInterfaceServ;

import java.util.List;

public class projetImplService implements projetInterfaceServ {

    private projectRepoInterface projectRepoInterface;

    public projetImplService(projectRepoInterface projectRepoInterface) {
        this.projectRepoInterface = projectRepoInterface;
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
    projectRepoInterface.addProject(projet);
    }
}

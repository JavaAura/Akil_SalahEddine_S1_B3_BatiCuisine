package org.aura.services.implementation;

import org.aura.models.Projet;
import org.aura.services.interfaces.projetInterfaceServ;

import java.util.List;

public class projetImplService implements projetInterfaceServ {

    public projetImplService() {
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

    }
}

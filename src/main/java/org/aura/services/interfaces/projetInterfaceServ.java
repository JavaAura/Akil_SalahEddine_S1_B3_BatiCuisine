package org.aura.services.interfaces;

import org.aura.models.Projet;

import java.util.List;

public interface projetInterfaceServ {
    List<Projet> getAllProjects();
    Projet getProject(int id);
    void createProject(Projet projet);
}

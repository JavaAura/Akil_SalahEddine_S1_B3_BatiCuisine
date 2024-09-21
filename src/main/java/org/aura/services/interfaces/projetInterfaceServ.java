package org.aura.services.interfaces;

import org.aura.models.Client;
import org.aura.models.Projet;

import java.util.List;

public interface projetInterfaceServ {
    List<Projet> getAllProjects();
    Projet getProject(int id);
    int createProject(Projet projet, Client client);
}

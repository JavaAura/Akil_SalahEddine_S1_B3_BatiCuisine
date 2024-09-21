package org.aura.repository.interfaces;

import org.aura.models.Projet;

import java.util.List;

public interface projectRepoInterface {
    List<Projet> findAllProjects();
    Projet findProjectById (int id);
    int addProject(Projet projet,int clientId);
}

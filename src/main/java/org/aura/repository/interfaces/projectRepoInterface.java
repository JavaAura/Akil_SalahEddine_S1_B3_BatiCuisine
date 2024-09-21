package org.aura.repository.interfaces;

import org.aura.models.Projet;

import java.util.List;

public interface projectRepoInterface {
    List<Projet> findAllProjects();
    Projet findProjectById (int id);
    void addProject(Projet projet,int clientId);
}

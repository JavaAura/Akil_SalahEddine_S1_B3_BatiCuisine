package org.aura.repository.interfaces;

import org.aura.models.Projet;
import org.aura.models.enums.Etat;

import java.util.List;

public interface projectRepoInterface {
    List<Projet> findAllProjects();
    Projet findProjectById (int id);
    int addProject(Projet projet,int clientId);
    void updateProject(int projectId, double coutTotal, double margeBeneficiaire);
    void updateEtatProject(int projectId , Etat etat);
}

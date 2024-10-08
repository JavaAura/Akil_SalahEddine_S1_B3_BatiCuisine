package org.aura.services.interfaces;

import org.aura.models.Client;
import org.aura.models.Projet;
import org.aura.models.enums.Etat;

import java.util.List;
import java.util.Map;

public interface projetInterfaceServ {
    Map<Integer,Projet> getAllProjects();
    Projet getProject(int id);
    int createProject(Projet projet, Client client);
    void updateProject (int projectId, double coutTotal, double margeBeneficiaire);
    void updateEtatProject (int projectId, Etat etat);

}

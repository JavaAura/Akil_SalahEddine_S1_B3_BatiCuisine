package org.aura.services.implementation;

import org.aura.models.Client;
import org.aura.models.Projet;
import org.aura.models.enums.Etat;
import org.aura.repository.implementation.projectRepoImpl;
import org.aura.services.interfaces.projetInterfaceServ;
import org.aura.utils.LoggerUtils;

import java.util.Map;
import java.util.Optional;

public class projetImplServ implements projetInterfaceServ {

    private final projectRepoImpl projectRepoImpl;

    public projetImplServ() {
        this.projectRepoImpl = new projectRepoImpl();
    }

    @Override
    public Map<Integer , Projet> getAllProjects() {
        return projectRepoImpl.findAllProjects();
    }

    @Override
    public Projet getProject(int id) {
       return Optional.ofNullable(projectRepoImpl.findProjectById(id)).orElseGet
                (()->{
                    LoggerUtils.logInfo("Pas de projet correspondent a ce ID");
                    return null;
                });
    }

    @Override
    public void updateProject(int projectId, double coutTotal, double margeBeneficiaire) {
        projectRepoImpl.updateProject( projectId,coutTotal,margeBeneficiaire);
    }

    @Override
    public void updateEtatProject(int projectId, Etat etat) {
        projectRepoImpl.updateEtatProject(projectId,etat);
    }

    @Override
    public int createProject(Projet projet, Client client) {
        return projectRepoImpl.addProject(projet,client.getId());
    }
}

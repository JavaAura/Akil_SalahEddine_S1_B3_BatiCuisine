package org.aura.repository.interfaces;

import org.aura.models.workforce;

public interface mainDoeuvreRepoInterface {
    workforce findWorkForceById(int id) ;
    void addWorkForce (workforce mainDoeuvre,int projectId);
}

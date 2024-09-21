package org.aura.repository.interfaces;

import org.aura.models.Materiel;

public interface materielRepoInterface {
    Materiel findMaterielById(int id) ;
    void addMateriel (Materiel materiel,int idProjet);
}

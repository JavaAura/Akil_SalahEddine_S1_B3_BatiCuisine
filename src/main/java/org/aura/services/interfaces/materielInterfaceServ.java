package org.aura.services.interfaces;

import org.aura.models.Materiel;
import org.aura.models.Projet;

public interface materielInterfaceServ {
    Materiel getMateriel(int id);
    void createMateriel(Materiel materiel ,int projetId);
}

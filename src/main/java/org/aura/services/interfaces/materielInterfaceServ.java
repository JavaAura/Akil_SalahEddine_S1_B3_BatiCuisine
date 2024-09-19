package org.aura.services.interfaces;

import org.aura.models.Materiel;

public interface materielInterfaceServ {
    Materiel getMateriel(int id);
    void createMateriel(Materiel materiel);
}

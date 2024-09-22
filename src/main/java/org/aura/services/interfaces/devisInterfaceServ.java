package org.aura.services.interfaces;

import org.aura.models.Devis;
import org.aura.models.Materiel;

public interface devisInterfaceServ {
    Devis getDevis(int id);
    void createDevis(Devis devis,int projectId);
}

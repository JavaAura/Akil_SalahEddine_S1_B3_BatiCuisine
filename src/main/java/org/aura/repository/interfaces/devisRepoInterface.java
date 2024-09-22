package org.aura.repository.interfaces;

import org.aura.models.Devis;

public interface devisRepoInterface {
    Devis findDevisById(int id) ;
    void addDevis (Devis devis,int projectId);
}

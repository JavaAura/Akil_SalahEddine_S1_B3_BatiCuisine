package org.aura.services.implementation;

import org.aura.models.Materiel;
import org.aura.models.Projet;
import org.aura.repository.implementation.materielRepoImpl;
import org.aura.services.interfaces.materielInterfaceServ;
import static org.aura.utils.LoggerUtils.*;

import java.util.List;
import java.util.Optional;

public class materielImpServ implements materielInterfaceServ {

    private final materielRepoImpl materielRepo;

    public materielImpServ() {
        this.materielRepo = new materielRepoImpl();
    }

    @Override
    public Materiel getMateriel(int id) {
        return Optional.ofNullable(materielRepo.findMaterielById(id)).orElseGet(() -> {
            logInfo("Aucun materiel avec ce id");
            return null;
        });
    }

    @Override
    public void createMateriel(Materiel materiel, int projetId) {
        if (materiel != null){
            materielRepo.addMateriel(materiel, projetId);
        }else {
            logWarn("Materiel not created");
        }
    }


}

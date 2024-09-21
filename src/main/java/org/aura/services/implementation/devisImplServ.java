package org.aura.services.implementation;

import org.aura.models.Devis;
import org.aura.repository.implementation.devisRepoImpl;
import org.aura.services.interfaces.devisInterfaceServ;
import org.aura.utils.LoggerUtils;

import java.util.Optional;

public class devisImplServ implements devisInterfaceServ {
    private final devisRepoImpl devisRepo;

    public devisImplServ() {
        this.devisRepo = new devisRepoImpl();
    }

    @Override
    public Devis getDevis(int id) {
        return Optional.ofNullable(devisRepo.findDevisById(id)).orElseGet(()->{
            LoggerUtils.logWarn("Devis introuvable");
            return null;
        });
    }

    @Override
    public void createDevis(Devis devis) {
        if (devis != null){
            devisRepo.addDevis(devis);
        }else {
            LoggerUtils.logWarn("Devis not created");
        }
    }
}

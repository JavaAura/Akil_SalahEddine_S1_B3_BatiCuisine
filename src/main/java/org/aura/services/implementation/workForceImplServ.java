package org.aura.services.implementation;

import org.aura.models.workforce;
import org.aura.repository.implementation.mainDoeuvreRepoImpl;
import org.aura.services.interfaces.workForceInterfaceServ;
import org.aura.utils.LoggerUtils;


import java.util.Optional;

public class workForceImplServ implements workForceInterfaceServ {

    private final mainDoeuvreRepoImpl mainDoeuvreRepo;

    public workForceImplServ() {
        this.mainDoeuvreRepo = new mainDoeuvreRepoImpl() ;
    }

    @Override
    public workforce getWorkForce(int id) {
        return Optional.ofNullable(mainDoeuvreRepo.findWorkForceById(id))
                .orElseGet(()-> {
                    LoggerUtils.logInfo("Main d'oeuvre no trouvé");
                    return null;
                });
    }

    @Override
    public void createWorkForce(workforce workforce) {
        if (workforce!=null){
            mainDoeuvreRepo.addWorkForce(workforce);
        }else {
            LoggerUtils.logInfo("Mai d'oeuvre n'a pas créer");
        }
    }
}

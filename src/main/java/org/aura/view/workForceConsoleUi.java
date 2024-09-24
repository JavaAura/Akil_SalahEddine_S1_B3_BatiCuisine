package org.aura.view;

import org.aura.models.enums.mainDoeuvreType;
import org.aura.models.workforce;
import org.aura.services.implementation.workForceImplServ;
import org.aura.utils.InputValidation;
import org.aura.utils.LoggerUtils;


public class workForceConsoleUi {

private workForceImplServ workForceImplServ;

public workForceConsoleUi(){
    this.workForceImplServ = new workForceImplServ();
}

    public void ajouterMainOeuvre( int projetId){
        LoggerUtils.logInfo("--- Ajout des matériaux ---");
        String ajouterAutreMateriel ;
        do {

            LoggerUtils.logInfo("Entrez le nom du main-d'œuvre : ");
            String mainNom = InputValidation.ValidationString();
            LoggerUtils.logInfo("Entrez le taux TVA : ");
            double tauxTVA = InputValidation.validationDouble();
            LoggerUtils.logInfo("Entrez le type de main-d'œuvre : ");
            LoggerUtils.logInfo("1. Ouvrier");
            LoggerUtils.logInfo("2. Spécialiste");
            LoggerUtils.logInfo("Tapez 1 pour le type Ouvrier et 2 pour un Spécialiste ");
            int choix = InputValidation.validationInt();
            mainDoeuvreType laborType = null;
            if (choix == 1){
                laborType = mainDoeuvreType.Ouvrier;
            } else if (choix == 2) {
                laborType = mainDoeuvreType.Spécialiste;
            }else {
                LoggerUtils.logWarn("Choix invalide");
            }
            LoggerUtils.logInfo("Entrez le taux horaire : ");
            double tauxHoraire = InputValidation.validationDouble();
            LoggerUtils.logInfo("Entrez le nombre d'heures : ");
            int heuresTravail = InputValidation.validationInt();
            LoggerUtils.logInfo("Entrez le facteur de productivité : ");
            double productiviteOuvrier = InputValidation.validationDouble();
            workforce workforce = new workforce(mainNom,tauxTVA,tauxHoraire,heuresTravail,productiviteOuvrier,laborType);
            workForceImplServ.createWorkForce(workforce, projetId);
            LoggerUtils.logInfo("Main-d'œuvre ajoutée avec succès !");
            LoggerUtils.logInfo("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            ajouterAutreMateriel = InputValidation.validationYesNo();

        }while (ajouterAutreMateriel.equalsIgnoreCase("y"));

    }


}

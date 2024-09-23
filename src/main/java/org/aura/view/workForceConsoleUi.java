package org.aura.view;

import org.aura.models.enums.mainDoeuvreType;
import org.aura.models.workforce;
import org.aura.services.implementation.workForceImplServ;
import org.aura.utils.LoggerUtils;

import java.util.Scanner;

public class workForceConsoleUi {

private workForceImplServ workForceImplServ;

public workForceConsoleUi(){
    this.workForceImplServ = new workForceImplServ();
}

    public void ajouterMainOeuvre(Scanner scanner, int projetId){
        LoggerUtils.logInfo("--- Ajout des matériaux ---");
        String ajouterAutreMateriel ;
        do {
            scanner.nextLine();
            LoggerUtils.logInfo("Entrez le nom du main-d'œuvre : ");
            String mainNom = scanner.nextLine();
            LoggerUtils.logInfo("Entrez le taux TVA : ");
            double tauxTVA = scanner.nextDouble();
            LoggerUtils.logInfo("Entrez le type de main-d'œuvre : ");
            LoggerUtils.logInfo("1. Ouvrier");
            LoggerUtils.logInfo("2. Spécialiste");
            LoggerUtils.logInfo("Tapez 1 pour le type Ouvrier et 2 pour un Spécialiste ");
            int choix = scanner.nextInt();
            mainDoeuvreType laborType = null;
            if (choix == 1){
                laborType = mainDoeuvreType.Ouvrier;
            } else if (choix == 2) {
                laborType = mainDoeuvreType.Spécialiste;
            }else {
                LoggerUtils.logWarn("Choix invalide");
            }
            LoggerUtils.logInfo("Entrez le taux horaire : ");
            double tauxHoraire = scanner.nextDouble();
            LoggerUtils.logInfo("Entrez le nombre d'heures : ");
            int heuresTravail = scanner.nextInt();
            LoggerUtils.logInfo("Entrez le facteur de productivité : ");
            double productiviteOuvrier = scanner.nextDouble();
            workforce workforce = new workforce(mainNom,tauxTVA,tauxHoraire,heuresTravail,productiviteOuvrier,laborType);
            workForceImplServ.createWorkForce(workforce, projetId);
            LoggerUtils.logInfo("Main-d'œuvre ajoutée avec succès !");
            LoggerUtils.logInfo("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            ajouterAutreMateriel = scanner.next();

        }while (ajouterAutreMateriel.equalsIgnoreCase("y"));

    }


}

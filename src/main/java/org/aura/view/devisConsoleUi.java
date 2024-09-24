package org.aura.view;

import org.aura.models.Devis;
import org.aura.services.implementation.devisImplServ;
import org.aura.services.implementation.projetImplServ;
import org.aura.utils.DateUtilis;
import org.aura.utils.InputValidation;
import org.aura.utils.LoggerUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class devisConsoleUi {
    private devisImplServ devisImplServ;
    private projetImplServ projetImplServ;
    public devisConsoleUi() {
        this.devisImplServ = new devisImplServ();
    }
    public boolean createDevis(Scanner scanner,int projectId){
        LoggerUtils.logInfo("--- Enregistrement du Devis ---");
        LoggerUtils.logInfo("Entrez le montant estimer : ");
        double montantEstimer = InputValidation.validationDouble();
        scanner.nextLine();
        LocalDate dateE = null;

        do {
            LoggerUtils.logInfo("Entrez la date d'émission (format : jj/mm/aaaa) : ");
            String dateEmission = scanner.nextLine();

            if (DateUtilis.isValidDate(dateEmission)) {
                dateE = DateUtilis.dateValidation(dateEmission);
                break;
            } else {
                LoggerUtils.logInfo("Date invalide. Veuillez réessayer.");
            }
        } while (true);

        LocalDate dateV = null;
        do {
            LoggerUtils.logInfo("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
            String dateValidite = scanner.nextLine();
            if (DateUtilis.isValidDate(dateValidite)){
                dateV = DateUtilis.dateValidation(dateValidite);
                break;
            }else {
                LoggerUtils.logInfo("Date invalide. Veuillez réessayer.");
            }
        }while (true);
        LoggerUtils.logInfo("Veuillez acceptez ou réfusez le devis ");
        LoggerUtils.logInfo("1. Accepter ");
        LoggerUtils.logInfo("2. Refuser ");
        int choix = InputValidation.validationInt();
        scanner.nextLine();
        boolean accepte = false;
        if (choix == 1){
            accepte = true;
        }else if (choix==2){
            accepte = false;
        }else {
            LoggerUtils.logWarn("Option invalide");
        }
        LoggerUtils.logInfo("Souhaitez-vous enregistrer le devis ? (y/n) : ");
        String reponse = InputValidation.validationYesNo();
        if (reponse.equalsIgnoreCase("y")) {
            Devis devis = new Devis(montantEstimer,dateE,dateV,accepte);
            devisImplServ.createDevis(devis,projectId);
            LoggerUtils.logInfo("Devis enregistré avec succès !");
        } else {
            LoggerUtils.logInfo("Le devis n'a pas été enregistré.");
        }
        return accepte;
    }

    public  void afficherDevis(Scanner scanner) {
        System.out.println("--- Affichage d'un devis ---");
        System.out.print("Entrez l'ID du devis à afficher : ");
        int devisId = InputValidation.validationInt();
        scanner.nextLine();
        Devis devis = devisImplServ.getDevis(devisId);
        System.out.println("Détails du devis : " +devis );
    }

}

package org.aura.view;

import org.aura.models.Devis;
import org.aura.services.implementation.devisImplServ;
import org.aura.services.implementation.projetImplServ;
import org.aura.utils.DateUtilis;
import org.aura.utils.InputValidation;
import org.aura.utils.LoggerUtils;

import java.time.LocalDate;

public class devisConsoleUi {
    private devisImplServ devisImplServ;
    private projetImplServ projetImplServ;
    public devisConsoleUi() {
        this.devisImplServ = new devisImplServ();
    }
    public boolean createDevis(int projectId){
        LoggerUtils.logInfo("--- Enregistrement du Devis ---");
        LoggerUtils.logInfo("Entrez le montant estimer : ");
        double montantEstimer = InputValidation.validationDouble();

        LocalDate dateE = null;

        do {
            LoggerUtils.logInfo("Entrez la date d'émission (format : jj/mm/aaaa) : ");
            String dateEmission = InputValidation.ValidationString();

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
            String dateValidite = InputValidation.ValidationString();
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
        boolean accepte = false;
        int choix;
        do {
             choix = InputValidation.validationInt();
            if (choix == 1){
                accepte = true;
                LoggerUtils.logInfo("Devis accepté.");
            }else if (choix==2){
                accepte = false;
                LoggerUtils.logInfo("Devis refusé.");
            }else {
                LoggerUtils.logWarn("Option invalide");
            }
        }while (choix!=1 && choix!=2);

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

    public  void afficherDevis() {
        System.out.println("--- Affichage d'un devis ---");
        System.out.print("Entrez l'ID du devis à afficher : ");
        int devisId = InputValidation.validationInt();

        Devis devis = devisImplServ.getDevis(devisId);
        System.out.println("Détails du devis : " +devis );
    }

}

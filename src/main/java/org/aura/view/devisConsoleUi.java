package org.aura.view;

import org.aura.models.Devis;
import org.aura.services.implementation.devisImplServ;
import org.aura.utils.LoggerUtils;
import org.aura.utils.dateUtilis;

import java.time.LocalDate;
import java.util.Scanner;

public class devisConsoleUi {
    private devisImplServ devisImplServ;

    public devisConsoleUi() {
        this.devisImplServ = new devisImplServ();
    }
    public void createDevis(Scanner scanner,int projectId){
        LoggerUtils.logInfo("--- Enregistrement du Devis ---");
        LoggerUtils.logInfo("Entrez le montant estimer : ");
        double montantEstimer = scanner.nextDouble();
        scanner.nextLine();
        LoggerUtils.logInfo("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
        String dateEmission = scanner.nextLine();
        LoggerUtils.logInfo("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
        String dateValidite = scanner.nextLine();
        LoggerUtils.logInfo("Veuillez acceptez ou réfusez le devis ");
        LoggerUtils.logInfo("1. Accepter ");
        LoggerUtils.logInfo("2. Refuser ");
        int choix = scanner.nextInt();
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
        String reponse = scanner.nextLine();
        if (reponse.equalsIgnoreCase("y")) {
            LocalDate dateEmiss = dateUtilis.dateValidation(dateEmission);
            LocalDate dateV = dateUtilis.dateValidation(dateValidite);
            Devis devis = new Devis(montantEstimer,dateEmiss,dateV,accepte);
            devisImplServ.createDevis(devis,projectId);
            LoggerUtils.logInfo("Devis enregistré avec succès !");
        } else {
            LoggerUtils.logInfo("Le devis n'a pas été enregistré.");
        }
    }

    public  void afficherDevis(Scanner scanner) {
        System.out.println("--- Affichage d'un devis ---");
        System.out.print("Entrez l'ID du devis à afficher : ");
        int devisId = scanner.nextInt();
        scanner.nextLine();
        Devis devis = devisImplServ.getDevis(devisId);
        System.out.println("Détails du devis : " +devis );
    }

}

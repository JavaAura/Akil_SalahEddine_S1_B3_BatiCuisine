package org.aura.view;

import org.aura.models.Materiel;
import org.aura.services.implementation.materielImpServ;
import org.aura.utils.LoggerUtils;

import java.util.Scanner;

public class materialConsoleUi {

    private final materielImpServ materielImpServ;

    public materialConsoleUi() {
        this.materielImpServ = new materielImpServ();
    }

    public void ajouterMateriel(Scanner scanner){
        LoggerUtils.logInfo("--- Ajout des matériaux ---");
        String ajouterAutreMateriel ;
        do {
            scanner.nextLine();
            LoggerUtils.logInfo("Entrez le nom du matériau : ");
            String materialNom = scanner.nextLine();
            LoggerUtils.logInfo("Entrez la quantité de ce matériau : ");
            double quantite = scanner.nextDouble();
            LoggerUtils.logInfo("Entrez le taux TVA : ");
            double tauxTVA = scanner.nextDouble();
            LoggerUtils.logInfo("Entrez le coût unitaire de ce matériau : ");
            double coutUnitaire = scanner.nextDouble();
            LoggerUtils.logInfo("Entrez le coût de transport : ");
            double coutTransport = scanner.nextDouble();
            LoggerUtils.logInfo("Entrez le coefficient de qualité : ");
            double coefficientQualite = scanner.nextDouble();
            Materiel materiel = new Materiel(materialNom,tauxTVA,coutUnitaire,quantite,coutTransport,coefficientQualite);
            materielImpServ.createMateriel(materiel);
            LoggerUtils.logInfo("Matériau ajouté avec succès !");
            LoggerUtils.logInfo("Voulez-vous ajouter un autre matériau ? (y/n) : ");
            ajouterAutreMateriel = scanner.next();
        }while (ajouterAutreMateriel.equals("y"));
    }
}

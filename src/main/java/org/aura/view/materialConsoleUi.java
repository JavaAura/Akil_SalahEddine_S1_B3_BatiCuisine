package org.aura.view;

import org.aura.models.Materiel;
import org.aura.services.implementation.materielImpServ;
import org.aura.utils.InputValidation;
import org.aura.utils.LoggerUtils;


public class materialConsoleUi {

    private final materielImpServ materielImpServ;

    public materialConsoleUi() {
        this.materielImpServ = new materielImpServ();
    }

    public void ajouterMateriel( int projetId){
        LoggerUtils.logInfo("--- Ajout des matériaux ---");
        String ajouterAutreMateriel ;
        do {

            LoggerUtils.logInfo("Entrez le nom du matériau : ");
            String materialNom = InputValidation.ValidationString();
            LoggerUtils.logInfo("Entrez la quantité de ce matériau : ");
            double quantite = InputValidation.validationDouble();
            LoggerUtils.logInfo("Entrez le taux TVA : ");
            double tauxTVA = InputValidation.validationDouble();
            LoggerUtils.logInfo("Entrez le coût unitaire de ce matériau : ");
            double coutUnitaire = InputValidation.validationDouble();
            LoggerUtils.logInfo("Entrez le coût de transport : ");
            double coutTransport = InputValidation.validationDouble();
            LoggerUtils.logInfo("Entrez le coefficient de qualité : ");
            double coefficientQualite = InputValidation.validationDouble();
            Materiel materiel = new Materiel(materialNom,tauxTVA,coutUnitaire,quantite,coutTransport,coefficientQualite);
            materielImpServ.createMateriel(materiel,projetId);
            LoggerUtils.logInfo("Matériau ajouté avec succès !");
            LoggerUtils.logInfo("Voulez-vous ajouter un autre matériau ? (y/n) : ");
            ajouterAutreMateriel = InputValidation.validationYesNo();


        }while (ajouterAutreMateriel.equalsIgnoreCase("y"));
    }
}

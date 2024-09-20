package org.aura.view;

import org.aura.services.implementation.workForceImplServ;
import org.aura.utils.LoggerUtils;

import java.util.Scanner;

public class workForceConsoleUi {

private workForceImplServ workForceImplServ;

public workForceConsoleUi(){
    this.workForceImplServ = new workForceImplServ();
}

    public void ajouterMainOeuvre(Scanner scanner){
        LoggerUtils.logInfo("--- Ajout des matériaux ---");
        String ajouterAutreMateriel ;
        do {
            scanner.nextLine();
            LoggerUtils.logInfo("Entrez le nom du main-d'œuvre : ");
            String materialNom = scanner.nextLine();
            LoggerUtils.logInfo("Entrez le type composant : ");
            String typeComposant = scanner.nextLine();
            LoggerUtils.logInfo("Entrez le taux TVA : ");
            double tauxTVA = scanner.nextDouble();
            System.out.print("Entrez le type de main-d'œuvre : ");
            String laborType = scanner.nextLine();
            System.out.print("Entrez le taux horaire (€/h) : ");
            double tauxHoraire = scanner.nextDouble();
            System.out.print("Entrez le nombre d'heures : ");
            int heuresTravail = scanner.nextInt();
            System.out.print("Entrez le facteur de productivité : ");
            double productiviteOuvrier = scanner.nextDouble();
            System.out.println("Main-d'œuvre ajoutée avec succès !");
            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            ajouterAutreMateriel = scanner.next();
        }while (ajouterAutreMateriel.equals("y"));
    }


}

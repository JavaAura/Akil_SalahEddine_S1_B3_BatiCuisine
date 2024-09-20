package org.aura.view;

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
        String ajouterAutreMateriel ="";
        do {
            scanner.nextLine();
            System.out.print("Entrez le nom du matériau : ");
            String materialNom = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau : ");
            double quantity = scanner.nextDouble();
            System.out.print("Entrez le coût unitaire de ce matériau : ");
            double unitCost = scanner.nextDouble();
            System.out.print("Entrez le coût de transport : ");
            double transportCost = scanner.nextDouble();
            System.out.print("Entrez le coefficient de qualité : ");
            double qualityCoefficient = scanner.nextDouble();
            System.out.println("Matériau ajouté avec succès !");
        }while (ajouterAutreMateriel.equals("y"));
    }
}

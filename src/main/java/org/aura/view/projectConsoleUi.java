package org.aura.view;

import org.aura.models.Client;
import org.aura.models.Projet;
import org.aura.models.enums.Etat;
import org.aura.services.implementation.projetImplServ;
import org.aura.utils.LoggerUtils;

import static org.aura.utils.LoggerUtils.logInfo;

import java.util.Scanner;

public class projectConsoleUi {

    private final projetImplServ projetService;
    clientConsoleUi clientConsoleUi ;
    materialConsoleUi materialConsoleUi;
    workForceConsoleUi workForceConsoleUi;
    public projectConsoleUi() {
        this.projetService = new projetImplServ();
        this.clientConsoleUi = new clientConsoleUi();
        this.materialConsoleUi = new materialConsoleUi();
        this.workForceConsoleUi = new workForceConsoleUi();
    }

    public void creeProjet(Scanner scanner){
        logInfo("--- Recherche de client ---");
        logInfo("1. Chercher un client existant");
        logInfo("2. Ajouter un nouveau client");
        logInfo("Choisissez une option : ");
        int clientOption = scanner.nextInt();
        scanner.nextLine();
        if (clientOption<1 || clientOption>2){
            logInfo("Option invalide");
        }
        Client client;
        if (clientOption == 1) {
            client = clientConsoleUi.rechercheClientExist(scanner);
        } else {
            client = clientConsoleUi.ajouterClient(scanner);
        }
        scanner.nextLine();
        logInfo("--- Création d'un Nouveau Projet ---");
        logInfo("Entrez le nom du projet : ");
        String projectNom = scanner.nextLine();
        logInfo("Entrez la surface de la cuisine (en m²) : ");
        double surface = scanner.nextDouble();
        Projet projet = new Projet();
        projet.setNomProjet(projectNom);
        projet.setSurface(surface);
        projet.setEtatProjet(Etat.Encours);
        int projetId = projetService.createProject(projet, client);
        if (projetId == -1) {
            logInfo("Erreur lors de la création du projet.");
            return;
        }
         materialConsoleUi.ajouterMateriel(scanner, projetId);
         workForceConsoleUi.ajouterMainOeuvre(scanner, projetId);
    }

    public static void calculateProjectCout(Scanner scanner , materialConsoleUi materialConsoleUi ,workForceConsoleUi workForceConsoleUi,int projetId) {
        LoggerUtils.logInfo("--- Calcul du coût d'un projet ---");
        double coutTotalMateriaux = materialConsoleUi.ajouterMateriel(scanner, projetId);
        double coutTotalMainOeuvre = workForceConsoleUi.ajouterMainOeuvre(scanner, projetId);
        double coutTotalAvantMarge = coutTotalMateriaux + coutTotalMainOeuvre;
        LoggerUtils.logInfo("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        String appliqueMarge = scanner.next();
        double margeBeneficiaire = 0.0;
        if (appliqueMarge.equalsIgnoreCase("y")) {
            LoggerUtils.logInfo("Entrez le pourcentage de marge bénéficiaire (%) : ");
            double pourcentageMarge = scanner.nextDouble();
            margeBeneficiaire = coutTotalAvantMarge * (pourcentageMarge / 100);
            double coutTotalFinal = coutTotalAvantMarge + margeBeneficiaire;
            LoggerUtils.logInfo("Calcul du coût en cours...");
            System.out.println("--- Résultat du Calcul ---");
            System.out.println("Coût total avant marge : " + coutTotalAvantMarge + " DH");
            if (margeBeneficiaire > 0) {
                System.out.println("Marge bénéficiaire (" + (margeBeneficiaire / coutTotalAvantMarge * 100) + "%) : " + margeBeneficiaire + " DH");
            }
            System.out.println("Coût total final du projet : " + coutTotalFinal + " DH");
        }

    }
}

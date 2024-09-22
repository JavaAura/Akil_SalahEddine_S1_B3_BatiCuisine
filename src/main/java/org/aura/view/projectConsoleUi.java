package org.aura.view;

import org.aura.models.Client;
import org.aura.models.Materiel;
import org.aura.models.Projet;
import org.aura.models.enums.Etat;
import org.aura.models.workforce;
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
         afficherDetailsProjetEtCalculCout(scanner,projetId);
    }

    public void afficherDetailsProjetEtCalculCout(Scanner scanner, int projetId) {
        LoggerUtils.logInfo("--- Calcul du coût et affichage des détails du projet ---");

        Projet projet = projetService.getProject(projetId);
        if (projet != null) {
            System.out.println("--- Détails du Projet ---");
            System.out.println("Nom du projet : " + projet.getNomProjet());
            if (projet.getClient() != null) {
                System.out.println("Client : " + projet.getClient().getNom());
                System.out.println("Adresse du chantier : " + projet.getClient().getAdresse());
            } else {
                System.out.println("Client : Non défini");
            }
            System.out.println("Surface : " + projet.getSurface() + " m²");

            System.out.println("\n--- Détail des Matériaux ---");
            double coutTotalMateriaux = 0;
            for (Materiel materiel : projet.getMateriels()) {
                double coutMateriel = materiel.getQuantite() * materiel.getCoutUnitaire() + materiel.getCoutTransport();
                coutTotalMateriaux += coutMateriel;
                System.out.printf("- %s : %.2f € (quantité : %.2f, coût unitaire : %.2f €/unité, qualité : %.2f, transport : %.2f €)\n",
                        materiel.getNom(), coutMateriel, materiel.getQuantite(), materiel.getCoutUnitaire(), materiel.getCoefficientQualite(), materiel.getCoutTransport());
            }
            System.out.printf("**Coût total des matériaux avant TVA : %.2f €**\n", coutTotalMateriaux);
            System.out.printf("**Coût total des matériaux avec TVA (20%%) : %.2f €**\n", coutTotalMateriaux * 1.2);

            System.out.println("\n--- Détail de la Main-d'œuvre ---");
            double coutTotalMainOeuvre = 0;
            for (workforce mainDoeuvre : projet.getWorkforces()) {
                double coutMainOeuvre = mainDoeuvre.getHeuresTravail() * mainDoeuvre.getTauxHoraire();
                coutTotalMainOeuvre += coutMainOeuvre;
                System.out.printf("- %s : %.2f € (taux horaire : %.2f €/h, heures travaillées : %.2f h, productivité : %.2f)\n",
                        mainDoeuvre.getNom(), coutMainOeuvre, mainDoeuvre.getTauxHoraire(), mainDoeuvre.getHeuresTravail(), mainDoeuvre.getProductiviteOuvrier());
            }
            System.out.printf("**Coût total de la main-d'œuvre avant TVA : %.2f €**\n", coutTotalMainOeuvre);
            System.out.printf("**Coût total de la main-d'œuvre avec TVA (20%%) : %.2f €**\n", coutTotalMainOeuvre * 1.2);

            double coutTotalAvantMarge = coutTotalMateriaux + coutTotalMainOeuvre;
            System.out.printf("\n--- Coût total avant marge : %.2f € ---\n", coutTotalAvantMarge);

            LoggerUtils.logInfo("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
            String appliqueMarge = scanner.next();
            double margeBeneficiaire = 0.0;
            if (appliqueMarge.equalsIgnoreCase("y")) {
                LoggerUtils.logInfo("Entrez le pourcentage de marge bénéficiaire (%) : ");
                double pourcentageMarge = scanner.nextDouble();
                margeBeneficiaire = coutTotalAvantMarge * (pourcentageMarge / 100);
            }

            double coutTotalFinal = coutTotalAvantMarge + margeBeneficiaire;
            System.out.printf("Marge bénéficiaire (%.2f%%) : %.2f €\n", (margeBeneficiaire / coutTotalAvantMarge) * 100, margeBeneficiaire);
            System.out.printf("**Coût total final du projet : %.2f €**\n", coutTotalFinal);
        } else {
            LoggerUtils.logError("Projet non trouvé.");
        }
    }


}

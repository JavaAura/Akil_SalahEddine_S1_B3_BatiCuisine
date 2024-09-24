package org.aura.view;

import org.aura.models.Client;
import org.aura.models.Materiel;
import org.aura.models.Projet;
import org.aura.models.enums.Etat;
import org.aura.models.workforce;
import org.aura.services.implementation.projetImplServ;
import org.aura.utils.InputValidation;
import org.aura.utils.LoggerUtils;

import static org.aura.utils.LoggerUtils.logInfo;

import java.util.Map;


public class projectConsoleUi {

    private final projetImplServ projetService;
    clientConsoleUi clientConsoleUi ;
    materialConsoleUi materialConsoleUi;
    workForceConsoleUi workForceConsoleUi;
    devisConsoleUi devisConsoleUi;
    public projectConsoleUi() {
        this.projetService = new projetImplServ();
        this.clientConsoleUi = new clientConsoleUi();
        this.materialConsoleUi = new materialConsoleUi();
        this.workForceConsoleUi = new workForceConsoleUi();
        this.devisConsoleUi = new devisConsoleUi();
    }

    public void creeProjet(){
        logInfo("--- Recherche de client ---");
        logInfo("1. Chercher un client existant");
        logInfo("2. Ajouter un nouveau client");
        logInfo("Choisissez une option : ");
        int clientOption;
        do {
            clientOption = InputValidation.validationInt();
            if (clientOption<1 || clientOption>2){
                logInfo("Option invalide");
            }

        }while (clientOption != 1 && clientOption !=2 );

        Client client;
        if (clientOption == 1) {
            client = clientConsoleUi.rechercheClientExist();
        } else {
            client = clientConsoleUi.ajouterClient();
        }

        logInfo("--- Création d'un Nouveau Projet ---");
        logInfo("Entrez le nom du projet : ");
        String projectNom = InputValidation.ValidationString();
        logInfo("Entrez la surface de la cuisine (en m²) : ");
        double surface = InputValidation.validationDouble();
        Projet projet = new Projet();
        projet.setNomProjet(projectNom);
        projet.setSurface(surface);
        projet.setEtatProjet(Etat.Encours);
        int projetId = projetService.createProject(projet, client);
        if (projetId == -1) {
            logInfo("Erreur lors de la création du projet.");
            return;
        }
         materialConsoleUi.ajouterMateriel(projetId);
         workForceConsoleUi.ajouterMainOeuvre(projetId);
         afficherDetailsProjetEtCalculCout(projetId);
       boolean accepte = devisConsoleUi.createDevis(projetId);
       if (accepte){
           projetService.updateEtatProject(projetId,Etat.Terminé);
       }else {
           projetService.updateEtatProject(projetId,Etat.Annulé);
       }
    }

    public void afficherDetailsProjetEtCalculCout( int projetId) {
        LoggerUtils.logInfo("--- Calcul du coût et affichage des détails du projet ---");
        LoggerUtils.logInfo("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
        String appliqueMarge = InputValidation.validationYesNo();
        double margeBeneficiaire = 0.0;
        LoggerUtils.logInfo("Entrez le pourcentage de marge bénéficiaire (%) : ");
        double pourcentageMarge = InputValidation.validationDouble();

        Projet projet = projetService.getProject(projetId);
        if (projet != null) {
            System.out.println("--- Détails du Projet ---");
            System.out.println("Nom du projet : " + projet.getNomProjet());
            if (projet.getClient() != null) {
                System.out.println("Client : " + projet.getClient().getNom());
                System.out.println("Adresse du chantier : " + projet.getClient().getAdresse());
            } else {
               LoggerUtils.logWarn("Client : Non défini");
            }
            System.out.println("Surface : " + projet.getSurface() + " m²");
            double coutTotalMateriaux = displayMateriels(projet);
            double coutTotalMainOeuvre = displayMainDoeuvre(projet);
            double coutTotalAvantMarge = (coutTotalMateriaux + coutTotalMainOeuvre);
            System.out.printf("\n--- Coût total avant marge : %.2f DH  ---\n", coutTotalAvantMarge);

            if (appliqueMarge.equalsIgnoreCase("y")) {
                margeBeneficiaire = coutTotalAvantMarge * (pourcentageMarge / 100);
            }
            double coutTotalFinal = coutTotalAvantMarge + margeBeneficiaire;
            System.out.printf("Marge bénéficiaire (%.2f%%) : %.2f DH \n", (margeBeneficiaire / coutTotalAvantMarge) * 100, margeBeneficiaire);
            System.out.printf("**Coût total final du projet : %.2f DH **\n", coutTotalFinal);
            projetService.updateProject(projetId,coutTotalFinal,margeBeneficiaire);
        } else {
            LoggerUtils.logError("Projet non trouvé.");
        }
    }

    public double displayMateriels(Projet projet){
        System.out.println("1. Matériaux :");
        double coutTotalMateriaux = 0;
        double tva = 0 ;
        for (Materiel materiel : projet.getMateriels()) {
            tva = (materiel.getTauxTVA()/100)+1;
            double coutMateriel = (materiel.getQuantite() * materiel.getCoutUnitaire()*materiel.getCoefficientQualite()) + materiel.getCoutTransport();
            coutTotalMateriaux += coutMateriel;
            System.out.printf("- %s : %.2f DH (quantité : %.2f, coût unitaire : %.2f DH/unité, taux TVA :%.2f, qualité : %.2f, transport : %.2f DH)\n",
                    materiel.getNom(), coutMateriel, materiel.getQuantite(), materiel.getCoutUnitaire(),materiel.getTauxTVA(), materiel.getCoefficientQualite(), materiel.getCoutTransport());
        }
        System.out.printf("**Coût total des matériaux avant TVA : %.2f DH**\n", coutTotalMateriaux);
        System.out.printf("**Coût total des matériaux avec TVA (20%%) : %.2f DH**\n", coutTotalMateriaux * tva);
        return coutTotalMateriaux * tva;
    }
    public double displayMainDoeuvre(Projet projet) {
        System.out.println("2. Main-d'œuvre :");
        double coutTotalMainOeuvre = 0;
        double tva =0 ;
        for (workforce mainDoeuvre : projet.getWorkforces()) {
            tva = (mainDoeuvre.getTauxTVA()/100)+1;
            double coutMainOeuvre = mainDoeuvre.getHeuresTravail() * mainDoeuvre.getTauxHoraire() * mainDoeuvre.getProductiviteOuvrier();
            coutTotalMainOeuvre += coutMainOeuvre;
            System.out.printf("- %s : %.2f DH (taux horaire : %.2f DH/h, taux TVA :%.2f, heures travaillées : %.2f h, productivité : %.2f)\n",
                    mainDoeuvre.getNom(), coutMainOeuvre, mainDoeuvre.getTauxHoraire(),mainDoeuvre.getTauxTVA(), mainDoeuvre.getHeuresTravail(), mainDoeuvre.getProductiviteOuvrier());
        }
        System.out.printf("**Coût total de la main-d'œuvre avant TVA : %.2f DH**\n", coutTotalMainOeuvre);
        System.out.printf("**Coût total de la main-d'œuvre avec TVA (20%%) : %.2f DH**\n", coutTotalMainOeuvre * tva);
        return coutTotalMainOeuvre * tva;
    }

    public void displayProject (int projetId){

        Projet projet = projetService.getProject(projetId);
        if (projet != null) {
            System.out.println("--- Détails du Projet ---");
            System.out.println("Nom du projet : " + projet.getNomProjet());
            if (projet.getClient() != null) {
               System.out.println("Client : " + projet.getClient().getNom());
               System.out.println("Adresse du chantier : " + projet.getClient().getAdresse());
            } else {
               LoggerUtils.logInfo("Client : Non défini");
            }
          System.out.println("Surface : " + projet.getSurface() + " m²");
          System.out.println("Etat de projet : "+projet.getEtatProjet());

            double coutTotalMateriaux =displayMateriels(projet);
            double coutTotalMainOeuvre = displayMainDoeuvre(projet);

            double coutTotalAvantMarge = coutTotalMateriaux + coutTotalMainOeuvre;
            System.out.printf("\n--- Coût total avant marge : %.2f DH  ---\n", coutTotalAvantMarge);


            System.out.printf("Marge bénéficiaire (%.2f%%) : %.2f DH \n", (projet.getMargeBeneficiaire() / coutTotalAvantMarge) * 100, projet.getMargeBeneficiaire());
            System.out.printf("**Coût total final du projet : %.2f DH **\n", projet.getCoutTotal());

        } else {
            LoggerUtils.logError("Projet non trouvé.");
        }
    }

    public void displayAllProjects() {
        Map<Integer, Projet> projets = projetService.getAllProjects();
        if (projets.isEmpty()) {
            LoggerUtils.logInfo("Aucun projet trouvé.");
            return;
        }
        for (Map.Entry<Integer, Projet> entry : projets.entrySet()) {
            Projet projet = entry.getValue();

            System.out.println("Nom du projet : " + projet.getNomProjet());
            if (projet.getClient() != null) {
                System.out.println("Client : " + projet.getClient().getNom());
                System.out.println("Adresse du chantier : " + projet.getClient().getAdresse());
            } else {
                System.out.println("Client : Non défini");
            }
            System.out.println("Surface : " + projet.getSurface() + " m²");
            System.out.println("Etat de projet : "+projet.getEtatProjet());

            System.out.println("--- Détail des Coûts ---");
            double coutTotalMateriaux =displayMateriels(projet);
            double coutTotalMainOeuvre = displayMainDoeuvre(projet);
            double coutTotalAvantMarge = coutTotalMateriaux  + coutTotalMainOeuvre;
            System.out.printf("3. Coût total avant marge : %.2f DH\n", coutTotalAvantMarge);
            double margeBeneficiaire = projet.getMargeBeneficiaire();
            double pourcentageMarge = (margeBeneficiaire / coutTotalAvantMarge) * 100;
            System.out.printf("4. Marge bénéficiaire (%.2f%%) : %.2f DH\n", pourcentageMarge, margeBeneficiaire);
            System.out.printf("**Coût total final du projet : %.2f DH**\n", projet.getCoutTotal());
            System.out.print("\n-----------------------------\n");
        }
    }
}

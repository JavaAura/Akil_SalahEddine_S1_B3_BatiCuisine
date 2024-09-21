package org.aura.view;

import org.aura.models.Client;
import org.aura.models.Projet;
import org.aura.services.implementation.projetImplServ;
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
        projetService.createProject(projet,client);
        materialConsoleUi.ajouterMateriel(scanner);
        workForceConsoleUi.ajouterMainOeuvre(scanner);
    }

    public static void main(String[] args) {
        projectConsoleUi projectConsoleUi  = new projectConsoleUi();
        projectConsoleUi.creeProjet(new Scanner(System.in));
    }
}

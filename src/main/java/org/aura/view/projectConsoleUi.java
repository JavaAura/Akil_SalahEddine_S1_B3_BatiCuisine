package org.aura.view;

import org.aura.services.implementation.projetImplService;
import static org.aura.utils.LoggerUtils.logInfo;

import java.util.Scanner;

public class projectConsoleUi {

    private final projetImplService projetService;
    clientConsoleUi clientConsoleUi = new clientConsoleUi();
    public projectConsoleUi() {
        this.projetService = new projetImplService( );
    }

    public void creeProjet(Scanner scanner){
        logInfo("--- Recherche de client ---");
        logInfo("1. Chercher un client existant");
        logInfo("2. Ajouter un nouveau client");
        logInfo("Choisissez une option : ");
        int clientOption = scanner.nextInt();
        if (clientOption<1 || clientOption>2){
            logInfo("Option invalide");
        }
        if (clientOption == 1) {
             clientConsoleUi.rechercheClientExist(scanner);
        } else {
            clientConsoleUi.ajouterClient(scanner);
        }
        logInfo("--- Création d'un Nouveau Projet ---");
        scanner.nextLine();
        logInfo("Entrez le nom du projet : ");
        String projectNom = scanner.nextLine();
        logInfo("Entrez la surface de la cuisine (en m²) : ");


    }
}

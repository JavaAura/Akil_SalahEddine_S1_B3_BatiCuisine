package org.aura.view;

import org.aura.utils.LoggerUtils;

import java.util.Scanner;

public class menuConsoleUi {

    public static void main(String[] args) {
        projectConsoleUi projectConsoleUi = new projectConsoleUi();

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            LoggerUtils.logInfo("=== Menu Principal ===");
            LoggerUtils.logInfo("1. Créer un nouveau projet");
            LoggerUtils.logInfo("2. Afficher les projets existants");
            LoggerUtils.logInfo("3. Calculer le coût d'un projet");
            LoggerUtils.logInfo("4. Quitter");
            LoggerUtils.logInfo("Choisissez une option : ");
            option = scanner.nextInt();
            switch (option){
                case 1 :
                    projectConsoleUi.creeProjet(scanner);
                    break;
                case 2 :
                    projectConsoleUi.displayAllProjects();
                    break;
                case 3 :
                    LoggerUtils.logInfo("Veuillez entre le Id  de projet : ");
                    int id = scanner.nextInt();
                    projectConsoleUi.displayProject(id);
                    break;
                case 4 :
                    LoggerUtils.logInfo("AU REVOIR :)");

            }

        }while (option!=4);
    }
}

package org.aura.view;

import org.aura.utils.InputValidation;
import org.aura.utils.LoggerUtils;



public class menuConsoleUi {

    public static void main(String[] args) {
        projectConsoleUi projectConsoleUi = new projectConsoleUi();


        int option;
        do {
            LoggerUtils.logInfo("=== Menu Principal ===");
            LoggerUtils.logInfo("1. Créer un nouveau projet");
            LoggerUtils.logInfo("2. Afficher les projets existants");
            LoggerUtils.logInfo("3. Calculer le coût d'un projet");
            LoggerUtils.logInfo("4. Quitter");
            LoggerUtils.logInfo("Choisissez une option : ");
            option = InputValidation.validationInt();
            switch (option){
                case 1 :
                    projectConsoleUi.creeProjet();
                    break;
                case 2 :
                    projectConsoleUi.displayAllProjects();
                    break;
                case 3 :
                    LoggerUtils.logInfo("Veuillez entre le Id  de projet : ");
                    int id = InputValidation.validationInt();
                    projectConsoleUi.displayProject(id);
                    break;
                case 4 :
                    LoggerUtils.logInfo("AU REVOIR :)");
                    break;
                default:
                    LoggerUtils.logWarn("Option invalide");
            }
        }while (option!=4);
    }
}

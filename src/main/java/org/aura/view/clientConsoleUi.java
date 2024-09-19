package org.aura.view;

import org.aura.models.Client;
import org.aura.services.implementation.clientImplService;
import org.aura.utils.LoggerUtils;

import java.util.Scanner;

public class clientConsoleUi {

    clientImplService clientImplService;

    public void rechercheClientExist(Scanner scanner){
        LoggerUtils.logInfo("--- Recherche de client existant --- ");
        scanner.nextLine();
        LoggerUtils.logInfo("Entrez le nom du client : ");
        String clientNom = scanner.nextLine();
        Client client = clientImplService.getClientByName(clientNom);
        if (client != null){
            LoggerUtils.logInfo("Client trouvé : " + client);
        }else {
            LoggerUtils.logInfo("Client no trouvé :)");
        }
    }
    public void ajouterClient(Scanner scanner){
        System.out.println("--- Ajout d'un nouveau client ---");
        scanner.nextLine();
        LoggerUtils.logInfo("Entrez le nom du client : ");
        String clientNom = scanner.nextLine();
        LoggerUtils.logInfo("Entrez l'adresse du client : ");
        String address = scanner.nextLine();
        LoggerUtils.logInfo("Entrez le numéro de téléphone du client : ");
        String phoneNumber = scanner.nextLine();
        LoggerUtils.logInfo("Entrez le type du client : ");
        LoggerUtils.logInfo("1. Professionnel");
        LoggerUtils.logInfo("2. régulier");
        LoggerUtils.logInfo("Tapez un 1 pour un client Professionnel ou 2 pour un client régulier");
        int choix = scanner.nextInt();
        if (choix<1 || choix>2){
            LoggerUtils.logWarn("Option invalide");
        }
        boolean estProfessionnel = false ;
        if (choix == 1){
            estProfessionnel = true;
        }
        Client client = new Client(clientNom,address,phoneNumber,estProfessionnel);
        clientImplService.createClient(client);
        LoggerUtils.logInfo("Client est ajouté " );
    }

}

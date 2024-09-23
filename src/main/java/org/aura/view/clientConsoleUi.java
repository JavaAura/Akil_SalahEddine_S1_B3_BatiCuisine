package org.aura.view;

import org.aura.models.Client;
import org.aura.services.implementation.clientImplServ;
import org.aura.utils.LoggerUtils;

import static org.aura.utils.LoggerUtils.logInfo;
import static org.aura.utils.LoggerUtils.logWarn;

import java.util.Scanner;

public class clientConsoleUi {

    private final clientImplServ clientImplService;

    public clientConsoleUi() {
        this.clientImplService = new clientImplServ();
    }

    public Client rechercheClientExist(Scanner scanner) {
        Client client = null;
        boolean trouve = false;
        String accepte = "n";
        do {
            logInfo("--- Recherche de client existant ---");
            logInfo("Entrez le nom du client : ");
            String clientNom = scanner.nextLine();
            client = clientImplService.getClientByName(clientNom);
            if (client != null) {
                logInfo("Client trouvé : " + client);
                logInfo("ID du client: " + client.getId());
                LoggerUtils.logInfo("Vous voulez continuez avec ce client (y/n) : ");
                accepte  = scanner.nextLine();
                if (accepte.equalsIgnoreCase("y")){
                    trouve = true;
                }
            }
        } while (!trouve);

        return client;
    }


    public Client ajouterClient(Scanner scanner){
        String accepte = "n";
        Client createdClient = null;
        do {
            logInfo("--- Ajout d'un nouveau client ---");
            logInfo("Entrez le nom du client : ");
            String clientNom = scanner.nextLine();
            logInfo("Entrez l'adresse du client : ");
            String address = scanner.nextLine();
            logInfo("Entrez le numéro de téléphone du client : ");
            String phoneNumber = scanner.nextLine();
            logInfo("Entrez le type du client : ");
            logInfo("1. Professionnel");
            logInfo("2. régulier");
            logInfo("Tapez un 1 pour un client Professionnel ou 2 pour un client régulier");
            int choix = scanner.nextInt();
            scanner.nextLine();
            if (choix<1 || choix>2){
                logWarn("Option invalide");
            }
            boolean estProfessionnel = false ;
            if (choix == 1){
                estProfessionnel = true;
            }
            Client client = new Client(clientNom,address,phoneNumber,estProfessionnel);
             createdClient = clientImplService.createClient(client);
            if (createdClient != null) {
                logInfo("Client ajouté avec succès. ID du client: " + createdClient.getId());
                LoggerUtils.logInfo("Vous voulez continuez avec ce client (y/n) : ");
                accepte  = scanner.nextLine();
            } else {
                logWarn("Erreur lors de l'ajout du client.");
            }
        }while (accepte.equalsIgnoreCase("n"));
        return createdClient;
    }

    public static void main(String[] args) {
        clientConsoleUi clientConsoleUi = new clientConsoleUi();
        Scanner scanner = new Scanner(System.in);
        clientConsoleUi.rechercheClientExist(scanner);
    }
}


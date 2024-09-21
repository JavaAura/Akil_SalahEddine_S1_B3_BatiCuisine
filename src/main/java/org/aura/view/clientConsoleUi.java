package org.aura.view;

import org.aura.models.Client;
import org.aura.services.implementation.clientImplServ;
import static org.aura.utils.LoggerUtils.logInfo;
import static org.aura.utils.LoggerUtils.logWarn;

import java.util.Scanner;

public class clientConsoleUi {

    private final clientImplServ clientImplService;

    public clientConsoleUi() {
        this.clientImplService = new clientImplServ();
    }
    public Client rechercheClientExist(Scanner scanner){
        logInfo("--- Recherche de client existant --- ");
        logInfo("\nEntrez le nom du client : ");
        String clientNom = scanner.nextLine();
        Client client = clientImplService.getClientByName(clientNom);
        if (client != null) {
            logInfo("Client trouvé : " + client);
            logInfo("ID du client: " + client.getId());
        } else {
            logInfo("Client non trouvé.");
            return null;
        }
        return client;
    }
    public Client ajouterClient(Scanner scanner){
        logInfo("--- Ajout d'un nouveau client ---");
        logInfo("\nEntrez le nom du client : ");
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
        Client createdClient = clientImplService.createClient(client);
        if (createdClient != null) {
            logInfo("Client ajouté avec succès. ID du client: " + createdClient.getId());
        } else {
            logWarn("Erreur lors de l'ajout du client.");
        }
        return createdClient;
    }

    public static void main(String[] args) {
        clientConsoleUi clientConsoleUi = new clientConsoleUi();
        Scanner scanner = new Scanner(System.in);
        clientConsoleUi.rechercheClientExist(scanner);
    }
}


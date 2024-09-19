package org.aura.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class clientConsoleUi {


    private static final Logger log = LoggerFactory.getLogger(clientConsoleUi.class);

    public void RecherchClientExist(Scanner scanner){
        log.info("--- Recherche de client existant --- ");
        scanner.nextLine();
        log.info("Entrez le nom du client : ");
        String clientNom = scanner.nextLine();

    }
}

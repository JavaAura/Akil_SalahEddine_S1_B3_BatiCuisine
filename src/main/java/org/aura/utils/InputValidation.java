package org.aura.utils;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class InputValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static String ValidationString() {
        String input = scanner.next().trim();
        while (input.isEmpty()) {
            LoggerUtils.logWarn("Erreur : l'entrée ne doit pas être vide. Veuillez entrer une valeur.");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static int validationInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                if (value <= 0) {
                    LoggerUtils.logWarn("Veuillez entrer un nombre entier supérieur à zéro.");
                }
                return  value;
            } catch (InputMismatchException e) {
                LoggerUtils.logWarn("Veuillez entrer un nombre entier valide.");
                scanner.next();
            }
        }
    }

    public static double validationDouble() {
        scanner.useLocale(Locale.US);
        while (true) {
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } else {
                LoggerUtils.logWarn("Erreur : veuillez entrer un nombre valide.");
                scanner.next();
            }
        }
    }

    private static boolean isValidName(String input) {
        return input != null && input.matches("[a-zA-Z\\s]+");
    }

    public static String ValidationName() {
        String input = scanner.next();
        if (!isValidName(input)) {
            LoggerUtils.logWarn("Erreur : le nom ne doit contenir que des lettres. Veuillez réessayer.");
          return ValidationName();
        }
        return input;
    }

    public static String validationPhoneNumber() {
            String numeroTelephone = scanner.next().trim();
            if (numeroTelephone.length() < 10 && !numeroTelephone.matches("^0[67]\\d{8}$") ) {
                LoggerUtils.logWarn("Le numéro de téléphone est invalide. Veuillez entrer un numéro valide d'au moins 10 caractères.");
                return validationPhoneNumber();
            }
            return numeroTelephone;
    }


    public static String validationYesNo() {
        String input;
        while (true) {
            input = scanner.next().trim().toLowerCase();
            if (input.equals("y") || input.equals("n") ) {
                return input;
            } else {
                LoggerUtils.logWarn("Erreur : veuillez entrer 'y', 'n'.");
            }
        }
    }
}

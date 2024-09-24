package org.aura.utils;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class InputValidation {

    private static final Scanner scanner = new Scanner(System.in);

    public static String ValidationString() {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("Erreur : l'entrée ne doit pas être vide. Veuillez entrer une valeur.");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static int validationInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                if (value <= 0) {
                    System.out.println("Veuillez entrer un nombre entier supérieur à zéro.");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre entier valide.");
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
                System.out.println("Erreur : veuillez entrer un nombre valide.");
                scanner.next();
            }
        }
    }

    private static boolean isValidName(String input) {
        return input != null && input.matches("[a-zA-Z\\s]+");
    }

    public static String ValidationName() {
        String input = scanner.nextLine().trim();
        while (!isValidName(input)) {
            System.out.println("Erreur : le nom ne doit contenir que des lettres. Veuillez réessayer.");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static String validationPhoneNumber() {
        while (true) {
            String numeroTelephone = scanner.nextLine().trim();
            if (numeroTelephone.length() < 10) {
                System.out.println("Le numéro de téléphone doit contenir au moins 10 caractères.");
                continue;
            }
            if (!numeroTelephone.matches("^0[67]\\d{8}$")) {
                System.out.println("Le numéro de téléphone est invalide. Veuillez entrer un numéro valide.");
                continue;
            }
            return numeroTelephone;
        }
    }

    public static String validationYesNo() {
        String input;
        while (true) {
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("n") ) {
                return input;
            } else {
                System.out.println("Erreur : veuillez entrer 'y', 'n'.");
            }
        }
    }
}

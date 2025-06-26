package at.fhburgenland.app;

import java.util.Scanner;

public class Zooverwaltung {
    private Scanner scanner = new Scanner(System.in);

    public Zooverwaltung() {
        // TODO Initialization of fields of Zooverwaltung
    }

    // TODO Implement body of Zooverwaltung
    public void run() {
        //logic goes here

        String menu = """
                Servus, willkommen bei der Zooverwaltung!
                Was möchtest du machen?
                1 - Gesundheitsakten der Tiere verwalten 
                2 - Tiere verwalten
                3 - todo
                4 - todo
                5 - TODO
                6 - TODO
                7 - TODO
                
                """;


        boolean runCode = true;

        while (runCode) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    printMenuGesundheitsakte();

                }
                default -> {
                    System.out.println("Die Eingabe '" + choice + "' war ungültig!");
                }
            }
        }
    }
}

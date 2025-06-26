package at.fhburgenland.menu;

import at.fhburgenland.entities.Besucher;
import at.fhburgenland.services.BesucherService;

import java.util.Scanner;

public class BesucherMenu {
    private final BesucherService service;
    private final Scanner scanner;

    public BesucherMenu(BesucherService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            System.out.println("""
                1 - Besucher anlegen
                2 - Besucher lesen
                3 - Besucher bearbeiten
                4 - Besucher löschen
                5 - Alle Besuchers anzeigen
                0 - Zurück
            """);
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> create();
                case "2" -> read();
                case "3" -> update();
                case "4" -> delete();
                case "5" -> listAll();
                case "0" -> back = true;
                default -> System.out.println("Ungültige Eingabe!");
            }
        }
    }

    private void create() {
        System.out.println("Vorname(verpflichtend):");
        String vorname = scanner.nextLine();
        System.out.println("Nachname:");
        String nachname = scanner.nextLine();

        Besucher besucher = new Besucher();
        besucher.setVorname(vorname);
        besucher.setNachname(nachname);

        service.addBesucher(besucher);
    }

    private void read() {
        System.out.println("Besucher-ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        Besucher obj = service.getBesucher(id);
        if (obj != null) {
            System.out.println(obj);
        } else {
            System.out.println("Besucher nicht gefunden.");
        }
    }
}

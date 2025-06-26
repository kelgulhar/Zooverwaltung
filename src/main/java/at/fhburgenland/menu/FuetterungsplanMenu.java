package at.fhburgenland.menu;

import at.fhburgenland.entities.Fuetterungsplan;
import at.fhburgenland.services.FuetterungsplanService;

import java.util.Scanner;

public class FuetterungsplanMenu {
    private final FuetterungsplanService service;
    private final Scanner scanner;

    public FuetterungsplanMenu(FuetterungsplanService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            System.out.println("""
                1 - Fuetterungsplan anlegen
                2 - Fuetterungsplan lesen
                3 - Fuetterungsplan bearbeiten
                4 - Fuetterungsplan löschen
                5 - Alle Fuetterungsplans anzeigen
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
        System.out.println("TODO: Fuetterungsplan anlegen");
        // Beispielhafte Eingabeaufforderung
    }

    private void read() {
        System.out.println("ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        Fuetterungsplan obj = service.getFuetterungsplan(id);
        if (obj != null) {
            System.out.println(obj);
        } else {
            System.out.println("Fuetterungsplan nicht gefunden.");
        }
    }

    private void update() {
        System.out.println("TODO: Fuetterungsplan bearbeiten");
        // Eingabe ID + neue Felder
    }

    private void delete() {
        System.out.println("ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        service.deleteFuetterungsplan(id);
    }

    private void listAll() {
        service.getAllFuetterungsplans().forEach(System.out::println);
    }
}

package at.fhburgenland.menu;

import at.fhburgenland.entities.Gesundheitsakte;
import at.fhburgenland.entities.Tier;
import at.fhburgenland.services.GesundheitsakteService;

import java.time.LocalDate;
import java.util.Scanner;

public class GesundheitsakteMenu {
    private final GesundheitsakteService service;
    private final Scanner scanner;

    public GesundheitsakteMenu(GesundheitsakteService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            System.out.println("""
                1 - Gesundheitsakte anlegen
                2 - Gesundheitsakte lesen
                3 - Gesundheitsakte bearbeiten
                4 - Gesundheitsakte löschen
                5 - Alle Gesundheitsaktes anzeigen
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
        try {
            System.out.println("Behandlungsart:");
            String behandlungsart = scanner.nextLine();

            System.out.println("Behandlungsdatum (yyyy-mm-dd):");
            LocalDate datum = LocalDate.parse(scanner.nextLine());

            System.out.println("Tier-ID:");
            int tierId = Integer.parseInt(scanner.nextLine());

            Gesundheitsakte ga = new Gesundheitsakte();
            ga.setBehandlungsart(behandlungsart);
            ga.setBehandlungsdatum(Date.valueOf(datum));
            Tier tier = new Tier();
            tier.setTierId(tierId); // Annahme: nur ID reicht (oder fetch über service)
            ga.setTier(tier);

            service.addGesundheitsakte(ga);
        } catch (Exception e) {
            System.out.println("Fehler bei der Eingabe: " + e.getMessage());
        }
    }

    private void read() {
        System.out.println("ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        Gesundheitsakte obj = service.getGesundheitsakte(id);
        if (obj != null) {
            System.out.println(obj);
        } else {
            System.out.println("Gesundheitsakte nicht gefunden.");
        }
    }

    private void update() {
        System.out.println("TODO: Gesundheitsakte bearbeiten");
        // Eingabe ID + neue Felder
    }

    private void delete() {
        System.out.println("ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        service.deleteGesundheitsakte(id);
    }

    private void listAll() {
        service.getAllGesundheitsaktes().forEach(System.out::println);
    }
}

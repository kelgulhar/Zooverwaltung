package at.fhburgenland.menu;

import at.fhburgenland.entities.Pfleger;
import at.fhburgenland.services.PflegerService;

import java.util.Scanner;

public class PflegerMenu {
    private final PflegerService service;
    private final Scanner scanner;

    public PflegerMenu(PflegerService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void show() {
        boolean back = false;

        while (!back) {
            System.out.println("""
                        1 - Pfleger anlegen
                        2 - Pfleger lesen
                        3 - Pfleger bearbeiten
                        4 - Pfleger löschen
                        5 - Alle Pflegers anzeigen
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
            System.out.println("Vorname:");
            String vorname = scanner.nextLine();

            System.out.println("Nachname:");
            String nachname = scanner.nextLine();

            System.out.println("SVNR (4-stellig):");
            String svnr = scanner.nextLine();

            Pfleger p = new Pfleger();
            p.setVorname(vorname);
            p.setNachname(nachname);
            p.setSvnr(svnr);

            service.addPfleger(p);
        } catch (Exception e) {
            System.out.println("Fehler bei der Eingabe: " + e.getMessage());
        }
    }
    // Beispielhafte Eingabeaufforderung


    private void read() {
        System.out.println("ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        Pfleger obj = service.getPfleger(id);
        if (obj != null) {
            System.out.println(obj);
        } else {
            System.out.println("Pfleger nicht gefunden.");
        }
    }

    private void update() {
        System.out.println("TODO: Pfleger bearbeiten");
        // Eingabe ID + neue Felder
    }

    private void delete() {
        System.out.println("ID eingeben:");
        int id = Integer.parseInt(scanner.nextLine());
        service.deletePfleger(id);
    }

    private void listAll() {
        service.getAllPflegers().forEach(System.out::println);
    }
}

//package at.fhburgenland.menu;
//
//import at.fhburgenland.entities.Nahrungsart;
//import at.fhburgenland.services.NahrungsartService;
//
//import java.util.Scanner;
//
//public class NahrungsartMenu {
//    private final NahrungsartService service;
//    private final Scanner scanner;
//
//    public NahrungsartMenu(NahrungsartService service, Scanner scanner) {
//        this.service = service;
//        this.scanner = scanner;
//    }
//
//    public void show() {
//        boolean back = false;
//
//        while (!back) {
//            System.out.println("""
//                1 - Nahrungsart anlegen
//                2 - Nahrungsart lesen
//                3 - Nahrungsart bearbeiten
//                4 - Nahrungsart löschen
//                5 - Alle Nahrungsarts anzeigen
//                0 - Zurück
//            """);
//            String input = scanner.nextLine();
//            switch (input) {
//                case "1" -> create();
//                case "2" -> read();
//                case "3" -> update();
//                case "4" -> delete();
//                case "5" -> listAll();
//                case "0" -> back = true;
//                default -> System.out.println("Ungültige Eingabe!");
//            }
//        }
//    }
//
//    private void create() {
//        System.out.println("TODO: Nahrungsart anlegen");
//        // Beispielhafte Eingabeaufforderung
//    }
//
//    private void read() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        Nahrungsart obj = service.getNahrungsart(id);
//        if (obj != null) {
//            System.out.println(obj);
//        } else {
//            System.out.println("Nahrungsart nicht gefunden.");
//        }
//    }
//
//    private void update() {
//        System.out.println("TODO: Nahrungsart bearbeiten");
//        // Eingabe ID + neue Felder
//    }
//
//    private void delete() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        service.deleteNahrungsart(id);
//    }
//
//    private void listAll() {
//        service.getAllNahrungsarts().forEach(System.out::println);
//    }
//}

//package at.fhburgenland.menu;
//
//import at.fhburgenland.entities.Fuehrung;
//import at.fhburgenland.services.FuehrungService;
//
//import java.util.Scanner;
//
//public class FuehrungMenu {
//    private final FuehrungService service;
//    private final Scanner scanner;
//
//    public FuehrungMenu(FuehrungService service, Scanner scanner) {
//        this.service = service;
//        this.scanner = scanner;
//    }
//
//    public void show() {
//        boolean back = false;
//
//        while (!back) {
//            System.out.println("""
//                1 - Fuehrung anlegen
//                2 - Fuehrung lesen
//                3 - Fuehrung bearbeiten
//                4 - Fuehrung löschen
//                5 - Alle Fuehrungs anzeigen
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
//        System.out.println("TODO: Fuehrung anlegen");
//        // Beispielhafte Eingabeaufforderung
//    }
//
//    private void read() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        Fuehrung obj = service.getFuehrung(id);
//        if (obj != null) {
//            System.out.println(obj);
//        } else {
//            System.out.println("Fuehrung nicht gefunden.");
//        }
//    }
//
//    private void update() {
//        System.out.println("TODO: Fuehrung bearbeiten");
//        // Eingabe ID + neue Felder
//    }
//
//    private void delete() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        service.deleteFuehrung(id);
//    }
//
//    private void listAll() {
//        service.getAllFuehrungs().forEach(System.out::println);
//    }
//}

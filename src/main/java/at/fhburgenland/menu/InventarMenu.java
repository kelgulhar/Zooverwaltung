//package at.fhburgenland.menu;
//
//import at.fhburgenland.entities.Inventar;
//import at.fhburgenland.services.InventarService;
//
//import java.util.Scanner;
//
//public class InventarMenu {
//    private final InventarService service;
//    private final Scanner scanner;
//
//    public InventarMenu(InventarService service, Scanner scanner) {
//        this.service = service;
//        this.scanner = scanner;
//    }
//
//    public void show() {
//        boolean back = false;
//
//        while (!back) {
//            System.out.println("""
//                1 - Inventar anlegen
//                2 - Inventar lesen
//                3 - Inventar bearbeiten
//                4 - Inventar löschen
//                5 - Alle Inventars anzeigen
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
//        System.out.println("TODO: Inventar anlegen");
//        // Beispielhafte Eingabeaufforderung
//    }
//
//    private void read() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        Inventar obj = service.getInventar(id);
//        if (obj != null) {
//            System.out.println(obj);
//        } else {
//            System.out.println("Inventar nicht gefunden.");
//        }
//    }
//
//    private void update() {
//        System.out.println("TODO: Inventar bearbeiten");
//        // Eingabe ID + neue Felder
//    }
//
//    private void delete() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        service.deleteInventar(id);
//    }
//
//    private void listAll() {
//        service.getAllInventars().forEach(System.out::println);
//    }
//}

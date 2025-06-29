//package at.fhburgenland.menu;
//
//import at.fhburgenland.services.GehegeService;
//
//import java.util.Scanner;
//
//public class GehegeMenu {
//    private final GehegeService service;
//    private final Scanner scanner;
//
//    public GehegeMenu(GehegeService service, Scanner scanner) {
//        this.service = service;
//        this.scanner = scanner;
//    }
//
//    public void show() {
//        boolean back = false;
//
//        while (!back) {
//            System.out.println("""
//                1 - Gehege anlegen
//                2 - Gehege lesen
//                3 - Gehege bearbeiten
//                4 - Gehege löschen
//                5 - Alle Geheges anzeigen
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
//        System.out.println("TODO: Gehege anlegen");
//        // Beispielhafte Eingabeaufforderung
//    }
//
//    private void read() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        Gehege obj = service.getGehege(id);
//        if (obj != null) {
//            System.out.println(obj);
//        } else {
//            System.out.println("Gehege nicht gefunden.");
//        }
//    }
//
//    private void update() {
//        System.out.println("TODO: Gehege bearbeiten");
//        // Eingabe ID + neue Felder
//    }
//
//    private void delete() {
//        System.out.println("ID eingeben:");
//        int id = Integer.parseInt(scanner.nextLine());
//        service.deleteGehege(id);
//    }
//
//    private void listAll() {
//        service.getAllGeheges().forEach(System.out::println);
//    }
//}

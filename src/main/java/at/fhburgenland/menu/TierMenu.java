//package at.fhburgenland.menu;
//
//import at.fhburgenland.entities.Gehege;
//import at.fhburgenland.entities.Tier;
//import at.fhburgenland.services.TierService;
//
//import java.util.Scanner;
//
//public class TierMenu {
//
//    private final TierService service;
//    private final Scanner scanner;
//
//    public TierMenu(TierService service, Scanner scanner) {
//        this.service = service;
//        this.scanner = scanner;
//    }
//
//    public void show() {
//        boolean back = false;
//
//        while (!back) {
//            System.out.println("""
//                1 - Tier anlegen
//                2 - Tier lesen
//                3 - Tier bearbeiten
//                4 - Tier löschen
//                5 - Alle Tiere anzeigen
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
//        try {
//            System.out.println("Tierart:");
//            String tierart = scanner.nextLine();
//            System.out.println("Name:");
//            String name = scanner.nextLine();
//            System.out.println("Alter:");
//            int alter = Integer.parseInt(scanner.nextLine());
//            System.out.println("Gehege-ID:");
//            int gehegeId = Integer.parseInt(scanner.nextLine());
//
//            Tier tier = new Tier();
//            tier.setTierart(tierart);
//            tier.setName(name);
//            tier.setAlter(alter);
//            Gehege gehege = new Gehege();
//            gehege.setGehegeId(gehegeId);
//            tier.setGehege(gehege);
//
//            service.addTier(tier);
//        } catch (Exception e) {
//            System.out.println("Fehler bei der Eingabe: " + e.getMessage());
//        }
//    }
//
//    private void read() {
//        try {
//            System.out.println("Tier-ID eingeben:");
//            int id = Integer.parseInt(scanner.nextLine());
//            Tier obj = service.getTier(id);
//            if (obj != null) {
//                System.out.println(obj);
//            } else {
//                System.out.println("Tier nicht gefunden.");
//            }
//        } catch (Exception e) {
//            System.out.println("Fehler: " + e.getMessage());
//        }
//    }
//
//    private void update() {
//        try {
//            System.out.println("Tier-ID eingeben:");
//            int id = Integer.parseInt(scanner.nextLine());
//
//            System.out.println("Neue Tierart:");
//            String tierart = scanner.nextLine();
//            System.out.println("Neuer Name:");
//            String name = scanner.nextLine();
//            System.out.println("Neues Alter:");
//            int alter = Integer.parseInt(scanner.nextLine());
//            System.out.println("Gehege-ID:");
//            int gehegeId = Integer.parseInt(scanner.nextLine());
//
//            Tier tier = new Tier();
//            tier.setTierId(id);
//            tier.setTierart(tierart);
//            tier.setName(name);
//            tier.setAlter(alter);
//            Gehege gehege = new Gehege();
//            gehege.setGehegeId(gehegeId);
//            tier.setGehege(gehege);
//
//            service.updateTier(id, tier);
//        } catch (Exception e) {
//            System.out.println("Fehler beim Bearbeiten: " + e.getMessage());
//        }
//    }
//
//    private void delete() {
//        try {
//            System.out.println("ID eingeben:");
//            int id = Integer.parseInt(scanner.nextLine());
//            service.deleteTier(id);
//        } catch (Exception e) {
//            System.out.println("Fehler beim Löschen: " + e.getMessage());
//        }
//    }
//
//    private void listAll() {
//        service.getAllTiers().forEach(System.out::println);
//    }
//}

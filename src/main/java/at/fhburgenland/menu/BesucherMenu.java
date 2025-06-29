package at.fhburgenland.menu;

import at.fhburgenland.entities.Besucher;
import at.fhburgenland.services.BesucherService;
import at.fhburgenland.services.FuehrungService;
import at.fhburgenland.util.Helper;

public class BesucherMenu {
    public BesucherMenu() {

    }

    public void run() {
        System.out.println("\n-- Besucher --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add besuchte Führung");
        System.out.println("5 = Delete");
        System.out.println("6 = Read besuchte Führungen");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Besucher b = new Besucher();
                b.setVorname(Helper.readStr("Vorname:"));
                b.setNachname(Helper.readStr("Nachname:"));
                BesucherService.create(b);
            }
            case 2 -> {
                int id = Helper.readInt("Besucher-ID:");
                System.out.println(BesucherService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Besucher-ID:");
                Besucher b = BesucherService.find(id);
                if (b != null) {
                    b.setVorname(Helper.readStr("Neuer Vorname:"));
                    b.setNachname(Helper.readStr("Neuer Nachname:"));
                    BesucherService.update(b);
                } else {
                    System.err.println("Abbruch: Besucher mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int bid = Helper.readInt("Besucher-ID:");
                if (BesucherService.find(bid) != null) {
                    int fid = Helper.readInt("Führung-ID:");
                    if (FuehrungService.find(fid) != null) {
                        BesucherService.createConnectionToFuehrung(bid, fid);
                    } else {
                        System.err.println("Abbruch: Führung mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Besucher mit dieser Id existiert nicht");
                }
            }
            case 5 -> BesucherService.delete(Helper.readInt("Besucher-ID:"));
            case 6 -> {
                // TODO implementieren
            }
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

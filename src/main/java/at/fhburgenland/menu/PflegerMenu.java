package at.fhburgenland.menu;

import at.fhburgenland.entities.Pfleger;
import at.fhburgenland.services.InventarService;
import at.fhburgenland.services.PflegerService;
import at.fhburgenland.util.Helper;

public class PflegerMenu {
    public PflegerMenu() {

    }

    public void run() {
        System.out.println("\n-- Pfleger --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add verwaltendes Inventaritem");
        System.out.println("5 = Delete");
        System.out.println("6 = Read Statistik");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Pfleger p = new Pfleger();
                p.setVorname(Helper.readStr("Vorname:"));
                p.setNachname(Helper.readStr("Nachname:"));
                p.setSvnr(Helper.readStr("SVNR:"));
                p.setGebDat(Helper.readDate("Geburtsdatum(DD.MM.YYYY):"));
                PflegerService.create(p);
            }
            case 2 -> {
                int id = Helper.readInt("Pfleger-ID:");
                System.out.println(PflegerService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Pfleger-ID:");
                Pfleger p = PflegerService.find(id);
                if (p != null) {
                    p.setVorname(Helper.readStr("Neuer Vorname:"));
                    p.setNachname(Helper.readStr("Neuer Nachname:"));
                    p.setSvnr(Helper.readStr("Neue SVNR:"));
                    p.setGebDat(Helper.readDate("Neues Geburtsdatum(DD.MM.YYYY):"));
                    PflegerService.update(p);
                } else {
                    System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int pid = Helper.readInt("Pfleger-ID:");
                if (PflegerService.find(pid) != null) {
                    int invId = Helper.readInt("Inventaritem-ID:");
                    if (InventarService.find(invId) != null) {
                        PflegerService.createConnectionToInventar(pid, invId);
                    } else {
                        System.err.println("Abbruch: Inventaritem mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                }
            }
            case 5 -> PflegerService.delete(Helper.readInt("Pfleger-ID:"));
            case 6 -> {
                // TODO Logik für Statistik
            }
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

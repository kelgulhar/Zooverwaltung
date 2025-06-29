package at.fhburgenland.menu;

import at.fhburgenland.entities.Gehege;
import at.fhburgenland.services.GehegeService;
import at.fhburgenland.services.PflegerService;
import at.fhburgenland.util.Helper;

public class GehegeMenu {
    public GehegeMenu() {

    }

    public void run() {
        System.out.println("\n-- Gehege --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add reinigender Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Gehege g = new Gehege();
                g.setGehegeart(Helper.readStr("Gehegeart:"));
                GehegeService.create(g);
            }
            case 2 -> {
                int id = Helper.readInt("Gehege-ID:");
                System.out.println(GehegeService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Gehege-ID:");
                Gehege g = GehegeService.find(id);
                if (g != null) {
                    g.setGehegeart(Helper.readStr("Neue Gehegeart:"));
                    GehegeService.update(g);
                } else {
                    System.err.println("Abbruch: Gehege mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int gId = Helper.readInt("Gehege-ID:");
                if (GehegeService.find(gId) != null) {
                    int pid = Helper.readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        GehegeService.createConnectionToPfleger(gId, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Gehege mit dieser Id existiert nicht");
                }
            }
            case 5 -> GehegeService.delete(Helper.readInt("Gehege-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

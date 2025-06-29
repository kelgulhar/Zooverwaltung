package at.fhburgenland.menu;

import at.fhburgenland.entities.Gesundheitsakte;
import at.fhburgenland.services.GesundheitsakteService;
import at.fhburgenland.services.TierService;
import at.fhburgenland.util.Helper;

public class GesundheitsakteMenu {
    public GesundheitsakteMenu() {

    }

    public void run() {
        System.out.println("\n-- Gesundheitsakte --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Gesundheitsakte ga = new Gesundheitsakte();
                ga.setBehandlungsart(Helper.readStr("Behandlungsart:"));
                ga.setBehandlungsdatum(Helper.readDate("Behandlungsdatum(DD.MM.YYYY):"));
                int tid = Helper.readInt("Tier-ID:");
                if (TierService.find(tid) != null) {
                    GesundheitsakteService.create(ga, tid);
                } else {
                    System.err.println("Abbruch: Das Tier mit dieser Id existiert nicht");
                }
            }
            case 2 -> {
                int id = Helper.readInt("Akte-ID:");
                System.out.println(GesundheitsakteService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Akte-ID:");
                Gesundheitsakte ga = GesundheitsakteService.find(id);
                if (ga != null) {
                    ga.setBehandlungsart(Helper.readStr("Neue Behandlungsart:"));
                    ga.setBehandlungsdatum(Helper.readDate("Neues Datum(DD.MM.YYYY):"));
                    int tid = Helper.readInt("Neue Tier-ID:");
                    if (TierService.find(tid) != null) {
                        GesundheitsakteService.update(ga);
                    } else {
                        System.err.println("Abbruch: Das Tier mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Gesundheitsakte mit dieser Id existiert nicht");
                }
            }
            case 4 -> GesundheitsakteService.delete(Helper.readInt("Akte-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

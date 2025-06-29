package at.fhburgenland.menu;

import at.fhburgenland.entities.Fuetterungsplan;
import at.fhburgenland.services.FuetterungsplanService;
import at.fhburgenland.services.PflegerService;
import at.fhburgenland.util.Helper;

public class FuetterungsplanMenu {
    public FuetterungsplanMenu() {

    }

    public void run() {
        System.out.println("\n-- Fütterungsplan --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add ausführenden Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Fuetterungsplan f = new Fuetterungsplan();
                f.setDatum(Helper.readDate("Datum(DD.MM.YYYY):"));
                f.setUhrzeit(Helper.readTime("Uhrzeit(HH:mm):"));
                FuetterungsplanService.create(f);
            }
            case 2 -> {
                int id = Helper.readInt("Plan-ID:");
                System.out.println(FuetterungsplanService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Plan-ID:");
                Fuetterungsplan f = FuetterungsplanService.find(id);
                if (f != null) {
                    f.setDatum(Helper.readDate("Neues Datum(DD.MM.YYYY):"));
                    f.setUhrzeit(Helper.readTime("Neue Uhrzeit(HH:mm):"));
                    FuetterungsplanService.update(f);
                } else {
                    System.err.println("Abbruch: Fütterungsplan mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int planId = Helper.readInt("Plan-ID:");
                if (FuetterungsplanService.find(planId) != null) {
                    int pid = Helper.readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        FuetterungsplanService.createConnectionToPfleger(planId, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Fütterungsplan mit dieser Id existiert nicht");
                }
            }
            case 5 -> FuetterungsplanService.delete(Helper.readInt("Plan-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

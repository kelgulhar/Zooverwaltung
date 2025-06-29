package at.fhburgenland.menu;

import at.fhburgenland.entities.Fuehrung;
import at.fhburgenland.services.FuehrungService;
import at.fhburgenland.services.PflegerService;
import at.fhburgenland.util.Helper;

public class FuehrungMenu {
    public FuehrungMenu() {

    }

    public void run() {
        System.out.println("\n-- Führung --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add veranstaltenden Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Fuehrung f = new Fuehrung();
                f.setDatum(Helper.readDate("Datum(DD.MM.YYYY):"));
                f.setUhrzeit(Helper.readTime("Uhrzeit(HH:mm):"));
                f.setGehegeroute(Helper.readStr("Gehegeroute:"));
                FuehrungService.create(f);
            }
            case 2 -> {
                int id = Helper.readInt("Führung-ID:");
                System.out.println(FuehrungService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Führung-ID:");
                Fuehrung f = FuehrungService.find(id);
                if (f != null) {
                    f.setDatum(Helper.readDate("Neues Datum(DD.MM.YYYY):"));
                    f.setUhrzeit(Helper.readTime("Neue Uhrzeit(HH:mm):"));
                    f.setGehegeroute(Helper.readStr("Neue Gehegeroute:"));
                    FuehrungService.update(f);
                } else {
                    System.err.println("Abbruch: Führung mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int fid = Helper.readInt("Führung-ID:");
                if (FuehrungService.find(fid) != null) {
                    int pid = Helper.readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        FuehrungService.createConnectionToPfleger(fid, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Führung mit dieser Id existiert nicht");
                }
            }
            case 5 -> FuehrungService.delete(Helper.readInt("Führung-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

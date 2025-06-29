package at.fhburgenland.menu;

import at.fhburgenland.entities.Nahrungsart;
import at.fhburgenland.services.FuetterungsplanService;
import at.fhburgenland.services.NahrungsartService;
import at.fhburgenland.util.Helper;

public class NahrungsartMenu {
    public NahrungsartMenu() {

    }

    public void run() {
        System.out.println("\n-- Nahrungsart --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add zugehürigen Fütterungsplan");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Nahrungsart n = new Nahrungsart();
                n.setBezeichnung(Helper.readStr("Bezeichnung:"));
                NahrungsartService.create(n);
            }
            case 2 -> {
                int id = Helper.readInt("Nahrungsart-ID:");
                System.out.println(NahrungsartService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Nahrungsart-ID:");
                Nahrungsart n = NahrungsartService.find(id);
                if (n != null) {
                    n.setBezeichnung(Helper.readStr("Neue Bezeichnung:"));
                    NahrungsartService.update(n);
                } else {
                    System.err.println("Abbruch: Nahrungsart mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int nid = Helper.readInt("Nahrung-ID:");
                if (NahrungsartService.find(nid) != null) {
                    int pid = Helper.readInt("Fütterungsplan-ID:");
                    if (FuetterungsplanService.find(pid) != null) {
                        NahrungsartService.createConnectionToFuetterung(nid, pid);
                    } else {
                        System.err.println("Abbruch: Fütterungsplan mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Nahrungsart mit dieser Id existiert nicht");
                }
            }
            case 5 -> NahrungsartService.delete(Helper.readInt("Nahrung-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

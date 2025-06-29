package at.fhburgenland.menu;

import at.fhburgenland.entities.Gehege;
import at.fhburgenland.entities.Tier;
import at.fhburgenland.services.GehegeService;
import at.fhburgenland.services.PflegerService;
import at.fhburgenland.services.TierService;
import at.fhburgenland.util.Helper;

import java.util.Scanner;

public class TierMenu {

    public TierMenu() {

    }

    public void run(){
        System.out.println("\n-- Tier --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add pflegender Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Tier t = new Tier();
                t.setName(Helper.readStr("Name:"));
                t.setTierart(Helper.readStr("Tierart:"));
                t.setAlter(Helper.readInt("Alter:"));
                int gId = Helper.readInt("Gehege-ID:");
                if (GehegeService.find(gId) != null) {
                    System.err.println("Abbruch: Das Gehege mit dieser Id existiert nicht");
                } else {
                    TierService.create(t, gId);
                }
            }
            case 2 -> {
                int id = Helper.readInt("Tier-ID:");
                System.out.println(TierService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Tier-ID:");
                Tier t = TierService.find(id);
                if (t != null) {
                    t.setName(Helper.readStr("Neuer Name:"));
                    t.setTierart(Helper.readStr("Neue Tierart:"));
                    t.setAlter(Helper.readInt("Neues Alter:"));
                    int gId = Helper.readInt("Neues Gehege-ID:");
                    if (GehegeService.find(gId) != null) {
                        System.err.println("Abbruch: Das Gehege mit dieser Id existiert nicht");
                    } else {
                        TierService.update(t);
                    }
                } else {
                    System.out.println("Tier mit dieser Id existieert nicht");
                }
            }
            case 4 -> {
                int tid = Helper.readInt("Tier-ID:");
                if (TierService.find(tid) != null) {
                    int pid = Helper.readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        TierService.createConnectionToPfleger(tid, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Tier mit dieser Id existiert nicht");
                }
            }
            case 5 -> TierService.delete(Helper.readInt("Tier-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

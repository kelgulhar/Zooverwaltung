package at.fhburgenland.menu;

import at.fhburgenland.entities.Inventar;
import at.fhburgenland.services.InventarService;
import at.fhburgenland.util.Helper;

public class InventarMenu {
    public InventarMenu() {

    }

    public void run() {
        System.out.println("\n-- Inventar --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Delete");
        System.out.println("0 = Back");
        int choice = Helper.readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Inventar i = new Inventar();
                i.setBezeichnung(Helper.readStr("Bezeichnung:"));
                InventarService.create(i);
            }
            case 2 -> {
                int id = Helper.readInt("Inventar-ID:");
                System.out.println(InventarService.find(id));
            }
            case 3 -> {
                int id = Helper.readInt("Inventar-ID:");
                Inventar i = InventarService.find(id);
                if (i != null) {
                    i.setBezeichnung(Helper.readStr("Neue Bezeichnung:"));
                    InventarService.update(i);
                } else {
                    System.err.println("Abbruch: Inventar mit dieser Id existiert nicht");
                }
            }
            case 4 -> InventarService.delete(Helper.readInt("Inventar-ID:"));
            case 0 -> {
                return;
            }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

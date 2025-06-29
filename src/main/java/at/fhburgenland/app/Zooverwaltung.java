package at.fhburgenland.app;

import at.fhburgenland.entities.*;
import at.fhburgenland.menu.*;
import at.fhburgenland.services.*;
import at.fhburgenland.util.Helper;

public class Zooverwaltung {

    public Zooverwaltung() {

    }

    public void run() {
        while (true) {
            showMainMenu();
            int choice = Helper.readInt("Auswahl:");
            switch (choice) {
                case 1:
                    handleTierMenu();
                    break;
                case 2:
                    handlePflegerMenu();
                    break;
                case 3:
                    handleGehegeMenu();
                    break;
                case 4:
                    handleAkteMenu();
                    break;
                case 5:
                    handlePlanMenu();
                    break;
                case 6:
                    handleFuehrungMenu();
                    break;
                case 7:
                    handleBesucherMenu();
                    break;
                case 8:
                    handleNahrungMenu();
                    break;
                case 9:
                    handleInventarMenu();
                    break;
                case 0:
                    System.out.println("Exit.");
                    return;
                default:
                    System.out.println("Ungültige Nummer.");
                    break;
            }
        }
    }

    // Hilfsmethoden für die Logik
    private void showMainMenu() {
        System.out.println("\n=== ZOO-VERWALTUNG ===");
        System.out.println("\t1  = Tier");
        System.out.println("\t2  = Pfleger");
        System.out.println("\t3  = Gehege");
        System.out.println("\t4  = Gesundheitsakte");
        System.out.println("\t5  = Fütterungsplan");
        System.out.println("\t6  = Führung");
        System.out.println("\t7  = Besucher");
        System.out.println("\t8  = Nahrungsart");
        System.out.println("\t9  = Inventar");
        System.out.println("\t0  = Beenden");
    }

    // Handle Methoden
    private void handleTierMenu() {
        TierMenu tierMenu = new TierMenu();
        tierMenu.run();
    }


    private void handleGehegeMenu() {
        GehegeMenu gehegeMenu = new GehegeMenu();
        gehegeMenu.run();
    }

    private void handleAkteMenu() {
        GesundheitsakteMenu gesundheitsakteMenu = new GesundheitsakteMenu();
        gesundheitsakteMenu.run();
    }

    private void handleInventarMenu() {
        InventarMenu inventarMenu = new InventarMenu();
        inventarMenu.run();
    }

    private void handlePflegerMenu() {
        PflegerMenu pflegerMenu = new PflegerMenu();
        pflegerMenu.run();
    }

    private void handleFuehrungMenu() {
        FuehrungMenu fuehrungMenu = new FuehrungMenu();
        fuehrungMenu.run();
    }

    private void handleBesucherMenu() {
        BesucherMenu besucherMenu = new BesucherMenu();
        besucherMenu.run();
    }

    private void handleNahrungMenu() {
        NahrungsartMenu nahrungsartMenu = new NahrungsartMenu();
        nahrungsartMenu.run();
    }

    private void handlePlanMenu() {
        FuetterungsplanMenu fuetterungsplanMenu = new FuetterungsplanMenu();
        fuetterungsplanMenu.run();
    }
}




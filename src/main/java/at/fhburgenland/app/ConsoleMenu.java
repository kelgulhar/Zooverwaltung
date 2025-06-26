package at.fhburgenland.app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import at.fhburgenland.entities.*;
import at.fhburgenland.entities.*;
import at.fhburgenland.services.*;

public class ConsoleMenu {
    private static Scanner in = new Scanner(System.in);

    public ConsoleMenu() {
    }

    public static void main(String[] args){
        run();
    }

    public static void run(){
        while (true) {
            showMainMenu();
            int choice = readInt("Auswahl:");
            switch (choice) {
                case 1: handleTierMenu();
                    break;
                case 2: handlePflegerMenu();
                    break;
                case 3: handleGehegeMenu();
                    break;
                case 4: handleAkteMenu();
                    break;
                case 5: handlePlanMenu();
                    break;
                case 6: handleFuehrungMenu();
                    break;
                case 7: handleBesucherMenu();
                    break;
                case 8: handleNahrungMenu();
                    break;
                case 9: handleInventarMenu();
                    break;
                case 0: System.out.println("Exit.");
                    return;
                default: System.out.println("Ungültige Nummer.");
                    break;
            }
        }
    }

    // Hilfsmethoden für die Logik
    private static void showMainMenu() {
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

    private static int readInt(String input) {
        System.out.println(input);
        return Integer.parseInt(in.nextLine());
    }

    private static String readStr(String input) {
        System.out.println(input);
        return in.nextLine();
    }

    private static LocalDate readDate(String input) {
        System.out.println(input);
        return LocalDate.parse(in.nextLine());
    }

    private static LocalTime readTime(String input) {
        System.out.println(input);
        return LocalTime.parse(in.nextLine());
    }

    // Handle Methoden
    private static void handleTierMenu(){
        System.out.println("\n-- Tier --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add pflegender Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Tier t = new Tier();
                t.setName(readStr("Name:"));
                t.setTierart(readStr("Tierart:"));
                t.setAlter(readInt("Alter:"));
                int gId = readInt("Gehege-ID:");
                if(GehegeService.find(gId) != null) {
                    System.err.println("Abbruch: Das Gehege mit dieser Id existiert nicht");
                }
                else {
                    TierService.create(t, gId);
                }
            }
            case 2 -> {
                int id = readInt("Tier-ID:");
                System.out.println(TierService.find(id));
            }
            case 3 -> {
                int id = readInt("Tier-ID:");
                Tier t = TierService.find(id);
                if(t != null) {
                    t.setName(readStr("Neuer Name:"));
                    t.setTierart(readStr("Neue Tierart:"));
                    t.setAlter(readInt("Neues Alter:"));
                    int gId = readInt("Neues Gehege-ID:");
                    if(GehegeService.find(gId) != null) {
                        System.err.println("Abbruch: Das Gehege mit dieser Id existiert nicht");
                    }
                    else {
                        TierService.update(t);
                    }
                }
                else {
                    System.out.println("Tier mit dieser Id existieert nicht");
                }
            }
            case 4 -> {
                int tid = readInt("Tier-ID:");
                if(TierService.find(tid) != null){
                    int pid = readInt("Pfleger-ID:");
                    if(PflegerService.find(pid) != null){
                        TierService.createConnectionToPfleger(tid, pid);
                    }else{
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                }
                else{
                    System.err.println("Abbruch: Tier mit dieser Id existiert nicht");
                }
            }
            case 5 -> TierService.delete(readInt("Tier-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }


    private static void handleGehegeMenu(){
        System.out.println("\n-- Gehege --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add reinigender Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Gehege g = new Gehege();
                g.setGehegeart(readStr("Gehegeart:"));
                GehegeService.create(g);
            }
            case 2 -> {
                int id = readInt("Gehege-ID:");
                System.out.println(GehegeService.find(id));
            }
            case 3 -> {
                int id = readInt("Gehege-ID:");
                Gehege g = GehegeService.find(id);
                if (g != null) {
                    g.setGehegeart(readStr("Neue Gehegeart:"));
                    GehegeService.update(g);
                } else {
                    System.err.println("Abbruch: Gehege mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int gId = readInt("Gehege-ID:");
                if (GehegeService.find(gId) != null) {
                    int pid = readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        GehegeService.createConnectionToPfleger(gId, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Gehege mit dieser Id existiert nicht");
                }
            }
            case 5 -> GehegeService.delete(readInt("Gehege-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handleAkteMenu(){
        System.out.println("\n-- Gesundheitsakte --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Gesundheitsakte ga = new Gesundheitsakte();
                ga.setBehandlungsart(readStr("Behandlungsart:"));
                ga.setBehandlungsdatum(readDate("Behandlungsdatum(YYYY-MM-dd):"));
                int tid = readInt("Tier-ID:");
                if(TierService.find(tid) != null){
                    GesundheitsakteService.create(ga, tid);
                } else{
                    System.err.println("Abbruch: Das Tier mit dieser Id existiert nicht");
                }
            }
            case 2 -> {
                int id = readInt("Akte-ID:");
                System.out.println(GesundheitsakteService.find(id));
            }
            case 3 -> {
                int id = readInt("Akte-ID:");
                Gesundheitsakte ga = GesundheitsakteService.find(id);
                if (ga != null) {
                    ga.setBehandlungsart(readStr("Neue Behandlungsart:"));
                    ga.setBehandlungsdatum(readDate("Neues Datum(YYYY-MM-dd):"));
                    int tid = readInt("Neue Tier-ID:");
                    if(TierService.find(tid) != null){
                        GesundheitsakteService.update(ga);
                    } else{
                        System.err.println("Abbruch: Das Tier mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Gesundheitsakte mit dieser Id existiert nicht");
                }
            }
            case 4 -> GesundheitsakteService.delete(readInt("Akte-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handleInventarMenu(){
        System.out.println("\n-- Inventar --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Inventar i = new Inventar();
                i.setBezeichnung(readStr("Bezeichnung:"));
                InventarService.create(i);
            }
            case 2 -> {
                int id = readInt("Inventar-ID:");
                System.out.println(InventarService.find(id));
            }
            case 3 -> {
                int id = readInt("Inventar-ID:");
                Inventar i = InventarService.find(id);
                if (i != null) {
                    i.setBezeichnung(readStr("Neue Bezeichnung:"));
                    InventarService.update(i);
                } else {
                    System.err.println("Abbruch: Inventar mit dieser Id existiert nicht");
                }
            }
            case 4 -> InventarService.delete(readInt("Inventar-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handlePflegerMenu(){
        System.out.println("\n-- Pfleger --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add verwaltendes Inventaritem");
        System.out.println("5 = Delete");
        System.out.println("6 = Read Statistik");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Pfleger p = new Pfleger();
                p.setVorname(readStr("Vorname:"));
                p.setNachname(readStr("Nachname:"));
                p.setSvnr(readStr("SVNR:"));
                p.setGebDat(readDate("Geburtsdatum(YYYY-MM-dd):"));
                PflegerService.create(p);
            }
            case 2 -> {
                int id = readInt("Pfleger-ID:");
                System.out.println(PflegerService.find(id));
            }
            case 3 -> {
                int id = readInt("Pfleger-ID:");
                Pfleger p = PflegerService.find(id);
                if (p != null) {
                    p.setVorname(readStr("Neuer Vorname:"));
                    p.setNachname(readStr("Neuer Nachname:"));
                    p.setSvnr(readStr("Neue SVNR:"));
                    p.setGebDat(readDate("Neues Geburtsdatum(YYYY-MM-dd):"));
                    PflegerService.update(p);
                } else {
                    System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int pid = readInt("Pfleger-ID:");
                if (PflegerService.find(pid) != null) {
                    int invId = readInt("Inventaritem-ID:");
                    if (InventarService.find(invId) != null) {
                        PflegerService.createConnectionToInventar(pid, invId);
                    } else {
                        System.err.println("Abbruch: Inventaritem mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                }
            }
            case 5 -> PflegerService.delete(readInt("Pfleger-ID:"));
            case 6 -> {
                // TODO Logik für Statistik
            }
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handleFuehrungMenu(){
        System.out.println("\n-- Führung --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add veranstaltenden Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Fuehrung f = new Fuehrung();
                f.setDatum(readDate("Datum(YYYY-MM-dd):"));
                f.setUhrzeit(readTime("Uhrzeit(HH:mm):"));
                f.setGehegeroute(readStr("Gehegeroute:"));
                FuehrungService.create(f);
            }
            case 2 -> {
                int id = readInt("Führung-ID:");
                System.out.println(FuehrungService.find(id));
            }
            case 3 -> {
                int id = readInt("Führung-ID:");
                Fuehrung f = FuehrungService.find(id);
                if (f != null) {
                    f.setDatum(readDate("Neues Datum(YYYY-MM-dd):"));
                    f.setUhrzeit(readTime("Neue Uhrzeit(HH:mm):"));
                    f.setGehegeroute(readStr("Neue Gehegeroute:"));
                    FuehrungService.update(f);
                } else {
                    System.err.println("Abbruch: Führung mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int fid = readInt("Führung-ID:");
                if (FuehrungService.find(fid) != null) {
                    int pid = readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        FuehrungService.createConnectionToPfleger(fid, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Führung mit dieser Id existiert nicht");
                }
            }
            case 5 -> FuehrungService.delete(readInt("Führung-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handleBesucherMenu(){
        System.out.println("\n-- Besucher --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add besuchte Führung");
        System.out.println("5 = Delete");
        System.out.println("6 = Read besuchte Führungen");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Besucher b = new Besucher();
                b.setVorname(readStr("Vorname:"));
                b.setNachname(readStr("Nachname:"));
                BesucherService.create(b);
            }
            case 2 -> {
                int id = readInt("Besucher-ID:");
                System.out.println(BesucherService.find(id));
            }
            case 3 -> {
                int id = readInt("Besucher-ID:");
                Besucher b = BesucherService.find(id);
                if (b != null) {
                    b.setVorname(readStr("Neuer Vorname:"));
                    b.setNachname(readStr("Neuer Nachname:"));
                    BesucherService.update(b);
                } else {
                    System.err.println("Abbruch: Besucher mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int bid = readInt("Besucher-ID:");
                if (BesucherService.find(bid) != null) {
                    int fid = readInt("Führung-ID:");
                    if (FuehrungService.find(fid) != null) {
                        BesucherService.createConnectionToFuehrung(bid, fid);
                    } else {
                        System.err.println("Abbruch: Führung mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Besucher mit dieser Id existiert nicht");
                }
            }
            case 5 -> BesucherService.delete(readInt("Besucher-ID:"));
            case 6 -> {
                // TODO implementieren
            }
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handleNahrungMenu(){
        System.out.println("\n-- Nahrungsart --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add zugehürigen Fütterungsplan");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Nahrungsart n = new Nahrungsart();
                n.setBezeichnung(readStr("Bezeichnung:"));
                NahrungsartService.create(n);
            }
            case 2 -> {
                int id = readInt("Nahrungsart-ID:");
                System.out.println(NahrungsartService.find(id));
            }
            case 3 -> {
                int id = readInt("Nahrungsart-ID:");
                Nahrungsart n = NahrungsartService.find(id);
                if (n != null) {
                    n.setBezeichnung(readStr("Neue Bezeichnung:"));
                    NahrungsartService.update(n);
                } else {
                    System.err.println("Abbruch: Nahrungsart mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int nid = readInt("Nahrung-ID:");
                if (NahrungsartService.find(nid) != null) {
                    int pid = readInt("Fütterungsplan-ID:");
                    if (FuetterungsplanService.find(pid) != null) {
                        NahrungsartService.createConnectionToFuetterung(nid, pid);
                    } else {
                        System.err.println("Abbruch: Fütterungsplan mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Nahrungsart mit dieser Id existiert nicht");
                }
            }
            case 5 -> NahrungsartService.delete(readInt("Nahrung-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }

    private static void handlePlanMenu(){
        System.out.println("\n-- Fütterungsplan --");
        System.out.println("1 = Create");
        System.out.println("2 = Read");
        System.out.println("3 = Update");
        System.out.println("4 = Add ausführenden Pfleger");
        System.out.println("5 = Delete");
        System.out.println("0 = Back");
        int choice = readInt("Auswahl:");
        switch (choice) {
            case 1 -> {
                Fuetterungsplan f = new Fuetterungsplan();
                f.setDatum(readDate("Datum(YYYY-MM-dd):"));
                f.setUhrzeit(readTime("Uhrzeit(HH:mm):"));
                FuetterungsplanService.create(f);
            }
            case 2 -> {
                int id = readInt("Plan-ID:");
                System.out.println(FuetterungsplanService.find(id));
            }
            case 3 -> {
                int id = readInt("Plan-ID:");
                Fuetterungsplan f = FuetterungsplanService.find(id);
                if (f != null) {
                    f.setDatum(readDate("Neues Datum(YYYY-MM-dd):"));
                    f.setUhrzeit(readTime("Neue Uhrzeit(HH:mm):"));
                    FuetterungsplanService.update(f);
                } else {
                    System.err.println("Abbruch: Fütterungsplan mit dieser Id existiert nicht");
                }
            }
            case 4 -> {
                int planId = readInt("Plan-ID:");
                if (FuetterungsplanService.find(planId) != null) {
                    int pid = readInt("Pfleger-ID:");
                    if (PflegerService.find(pid) != null) {
                        FuetterungsplanService.createConnectionToPfleger(planId, pid);
                    } else {
                        System.err.println("Abbruch: Pfleger mit dieser Id existiert nicht");
                    }
                } else {
                    System.err.println("Abbruch: Fütterungsplan mit dieser Id existiert nicht");
                }
            }
            case 5 -> FuetterungsplanService.delete(readInt("Plan-ID:"));
            case 0 -> { return; }
            default -> System.out.println("Ungültige Auswahl");
        }
    }
}

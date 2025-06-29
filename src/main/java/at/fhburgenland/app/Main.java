package at.fhburgenland.app;

import at.fhburgenland.util.JPAUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            disableHibernateInfoLogs();

            Zooverwaltung zw = new Zooverwaltung();
            zw.run();

        } catch (Exception e){
            System.err.println("Unbehandelte Exception in Main: " + e.getMessage());
        } finally {
            //EMF immer schließen
            JPAUtil.close();
        }
    }

    // Courtesy by Abdulhadi Rajeh and Michael Mayr <3
    public static void disableHibernateInfoLogs() {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }
}

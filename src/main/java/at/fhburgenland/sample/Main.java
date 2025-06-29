package at.fhburgenland.sample;

import jakarta.persistence.*;

import java.util.List;

/**
 * Sample code from lecture - do not run!
 * Persistence Unit
 */
public class Main {

    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("project");

    public static void main(String[] args) {
        EMF.createEntityManager();
        addPerson("Stefan", "Zuza", 120_000);
        addPerson("Sulykovski", "Flachovski", 100_000);
        addPerson("Marcel", "Dumitruvski", 100_000);
        printAllPersons();
        updatePerson(3, "Abba", "Zion", 4000);
        deletePerson(1);
        printAllPersons();
        /* TO DO
            -) Connect Database
            -) Klasse zur Tabelle erstellen!
            -) Create Methods for
                -) addPerson
                -) readPerson
                -) readAllPersons --> Ausgabe ganze Tabelle
                -) update Person
                -) delete Person
         */
        EMF.close();
    }

    public static void addPerson(String vorname, String nachname, int gehalt){
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();

            Person p = new Person(vorname, nachname, gehalt);

            em.persist(p);
            et.commit();
        } catch (Exception e) {
            if (et != null){
                et.rollback();
                System.err.println(e.getMessage());
            }
        }finally {
            em.close();
        }
    }

    public static void printAllPersons(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT p FROM Person p";
        TypedQuery<Person> tq = em.createQuery(query, Person.class);
        List<Person> personList = null;

        try {
            personList = tq.getResultList();
            for (Person person : personList){
                System.out.println(person.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void updatePerson(int pnr, String vorname, String nachname, int gehalt){
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Person p = null;

        try{
            et = em.getTransaction();
            et.begin();
            p = em.find(Person.class, pnr);
            p.setGehalt(gehalt);
            p.setNachname(nachname);
            p.setVorname(vorname);

            em.persist(p);
            et.commit();
            System.out.println("Person erfolgreich updated.");
        } catch (Exception e) {
            if (et != null){
                et.rollback();
            }
                System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void deletePerson(int pnr){
        EntityManager em = EMF.createEntityManager();
        EntityTransaction et = null;
        Person p = null;

        try{
            et = em.getTransaction();
            et.begin();
            p = em.find(Person.class, pnr);

            em.remove(p);
            et.commit();
            System.out.println("Person erfolgreich gelöscht.");
        } catch (Exception e) {
            if (et != null){
                et.rollback();
            }
                System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }
}

package at.fhburgenland.services;

import at.fhburgenland.entities.*;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class BesucherService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Besucher b) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(b);
            et.commit();
        } catch (Exception e) {
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void createConnectionToFuehrung(int besucherId, int fuehrungId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Besucher b = null;
        Fuehrung f = null;
        try {
            et = em.getTransaction();
            et.begin();

            b = em.find(Besucher.class, besucherId);
            f = em.find(Fuehrung.class, fuehrungId);

            if(f != null && b != null) {
                b.addFuehrung(f);
                f.addBesucher(b);
            }

            et.commit();
        } catch (Exception e) {
            if (et != null)
                et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static String getBesuchteFührungen(int besucherId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Besucher b = null;
        int besuchteFuehrungen = 0;
        try {
            et = em.getTransaction();
            et.begin();

            b = em.find(Besucher.class, besucherId);

            if(b != null){
                besuchteFuehrungen = b.getBesuchteFuehrungen().size();
            }

            // TODO Logik

            et.commit();
            return "";
        } catch (Exception e) {
            if (et != null)
                et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return "";
    }

    public static Besucher find(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Besucher.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public static void update(Besucher b) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Besucher existing = em.find(Besucher.class, b.getBesucherId());
            existing.setVorname(b.getVorname());
            existing.setNachname(b.getNachname());
            em.persist(existing);
            et.commit();
        } catch (Exception e) {
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Besucher b = em.find(Besucher.class, id);
            if (b != null) {
                b.getBesuchteFuehrungen().clear();
                em.remove(b);
            }
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static List<Besucher> findAll() {
        EntityManager em = emf.createEntityManager();
        String q = "SELECT b FROM Besucher b";
        TypedQuery<Besucher> tq = em.createQuery(q, Besucher.class);
        try {
            return tq.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}

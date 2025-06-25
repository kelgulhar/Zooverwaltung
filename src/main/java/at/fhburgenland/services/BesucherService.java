package at.fhburgenland.services;

import at.fhburgenland.entities.Besucher;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class BesucherService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void run(){
        // TODO Menu und Logik für Besucher
    }

    public static void create(Besucher b){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction(); et.begin();
            em.persist(b);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static Besucher find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Besucher.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Besucher b){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Besucher existing = em.find(Besucher.class, b.getBesucherId());
            existing.setVorname(b.getVorname());
            existing.setNachname(b.getNachname());
            // TODO: Min/Max-Notation prüfen (1..3 Führungen pro Besucher)
            em.persist(existing);
            et.commit();
        } catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static void delete(int id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Besucher b = em.find(Besucher.class, id);
            // TODO: Prüfen, ob nach Löschung keine  Mindestbesuche verletzt werden
            em.remove(b);
            et.commit();
        } catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static List<Besucher> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT b FROM Besucher b";
        TypedQuery<Besucher> tq = em.createQuery(q, Besucher.class);
        try{
            return tq.getResultList();
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        } finally{
            em.close();
        }
    }
}

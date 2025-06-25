package at.fhburgenland.services;

import at.fhburgenland.entities.Pfleger;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PflegerService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void run(){
        // TODO Menu und Logik für Pfleger
    }

    public static void create(Pfleger p){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction(); et.begin();
            em.persist(p);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static Pfleger find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Pfleger.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Pfleger p){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Pfleger existing = em.find(Pfleger.class, p.getPflegerId());
            existing.setVorname(p.getVorname());
            existing.setNachname(p.getNachname());
            existing.setSvnr(p.getSvnr());
            // TODO: Min/Max-Notation prüfen (0..10 Tiere, 0..5 Gehege/Führungen/Fütterungen/Inventar)
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
            Pfleger p = em.find(Pfleger.class, id);
            // TODO: Prüfen, ob nach Löschen alle Min-Anforderungen der Tiere/Führungen/Inventar Functions erfüllt sind
            em.remove(p);
            et.commit();
        } catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static List<Pfleger> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT p FROM Pfleger p";
        TypedQuery<Pfleger> tq = em.createQuery(q, Pfleger.class);
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

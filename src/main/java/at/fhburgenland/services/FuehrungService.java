package at.fhburgenland.services;

import at.fhburgenland.entities.*;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class FuehrungService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Fuehrung f, List<Integer> pflegerIds, List<Integer> besucherIds){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();

            for (Integer pid : pflegerIds) {
                Pfleger p = em.getReference(Pfleger.class, pid);
                f.addPfleger(p);
            }
            for (Integer bid : besucherIds) {
                Besucher b = em.getReference(Besucher.class, bid);
                f.addBesucher(b);
            }

            em.persist(f);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static Fuehrung find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Fuehrung.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Fuehrung f){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Fuehrung existing = em.find(Fuehrung.class, f.getFuehrungId());
            existing.setDatum(f.getDatum());
            existing.setUhrzeit(f.getUhrzeit());
            existing.setGehegeroute(f.getGehegeroute());
            em.persist(existing);
            et.commit();
        }
        catch (Exception e){
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
            Fuehrung f = em.find(Fuehrung.class, id);
            if (f != null) {
                f.getVeranstalter().clear();
                f.getBesucherListe().clear();
                em.remove(f);
            }
            et.commit();
        } catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static List<Fuehrung> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT f FROM Fuehrung f";
        TypedQuery<Fuehrung> tq = em.createQuery(q, Fuehrung.class);
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

package at.fhburgenland.services;

import at.fhburgenland.entities.Fuetterungsplan;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class FuetterungsplanService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Fuetterungsplan f){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction(); et.begin();
            em.persist(f);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static Fuetterungsplan find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Fuetterungsplan.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Fuetterungsplan f){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Fuetterungsplan existing = em.find(Fuetterungsplan.class, f.getPlanId());
            existing.setDatum(f.getDatum());
            existing.setUhrzeit(f.getUhrzeit());
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
            Fuetterungsplan f = em.find(Fuetterungsplan.class, id);
            em.remove(f);
            et.commit();
        } catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static List<Fuetterungsplan> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT f FROM Fuetterungsplan f";
        TypedQuery<Fuetterungsplan> tq = em.createQuery(q, Fuetterungsplan.class);
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

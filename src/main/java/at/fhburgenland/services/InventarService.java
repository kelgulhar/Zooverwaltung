package at.fhburgenland.services;

import at.fhburgenland.entities.Inventar;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class InventarService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Inventar inv){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction(); et.begin();
            em.persist(inv);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static Inventar find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Inventar.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Inventar i){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Inventar existing = em.find(Inventar.class, i.getInventarId());
            existing.setBezeichnung(i.getBezeichnung());
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
            Inventar i = em.find(Inventar.class, id);
            em.remove(i);
            et.commit();
        } catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static List<Inventar> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT i FROM Inventar i";
        TypedQuery<Inventar> tq = em.createQuery(q, Inventar.class);
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

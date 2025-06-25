package at.fhburgenland.services;

import at.fhburgenland.entities.Gehege;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class GehegeService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void run(){
        // TODO Menu und Logik für Gehege
    }

    public static void create(Gehege gehege){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction(); et.begin();
            em.persist(gehege);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static Gehege find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Gehege.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Gehege g){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Gehege existing = em.find(Gehege.class, g.getGehegeId());
            existing.setGehegeart(g.getGehegeart());
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
            Gehege g = em.find(Gehege.class, id);
            em.remove(g);
            et.commit();
        } catch (ConstraintViolationException cve){
            if(et.isActive()){
                et.rollback();
            }
            System.err.println(cve.getMessage());
        }
        catch (Exception e){
            if(et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public static List<Gehege> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT g FROM Gehege g";
        TypedQuery<Gehege> tq = em.createQuery(q, Gehege.class);
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

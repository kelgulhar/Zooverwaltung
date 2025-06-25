package at.fhburgenland.services;

import at.fhburgenland.entities.Fuehrung;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class FuehrungService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void run(){
        // TODO Menu und Logik für Führung
    }

    public static void create(Fuehrung f){
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
        } catch(ConstraintViolationException cve){
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

    public static void delete(int id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Fuehrung f = em.find(Fuehrung.class, id);
            em.remove(f);
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

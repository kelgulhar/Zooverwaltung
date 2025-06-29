package at.fhburgenland.services;

import at.fhburgenland.entities.*;
import at.fhburgenland.util.JPAUtil;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class FuehrungService {
    
    public static void create(Fuehrung f){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(f);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void createConnectionToPfleger(int fuehrungId, int pflegerId){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        Fuehrung f = null;
        Pfleger p = null;
        try {
            et = em.getTransaction();
            et.begin();

            f = em.find(Fuehrung.class, fuehrungId);
            p = em.find(Pfleger.class, pflegerId);

            if(f != null && p != null){
                p.addFuehrung(f);
                f.addPfleger(p);
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

    public static Fuehrung find(int id){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
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
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
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
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
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
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
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

package at.fhburgenland.services;

import at.fhburgenland.entities.Pfleger;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import at.fhburgenland.entities.*;

public class PflegerService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Pfleger p)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(p);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    // Create connection to Fuehrung
    public static void createConnectionToInventar(int pflegerId, int invId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Pfleger p = null;
        Inventar i = null;
        try {
            et = em.getTransaction();
            et.begin();

            p = em.find(Pfleger.class, pflegerId);
            i = em.find(Inventar.class, invId);

            if(i != null && p != null){
                p.addInventar(i);
                i.addPfleger(p);
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

    public static String getStatistics(int pflegerId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Pfleger p = null;
        Inventar i = null;
        try {
            et = em.getTransaction();
            et.begin();

            p = em.find(Pfleger.class, pflegerId);


            if(p != null){

            }

            // TODO Logik

            et.commit();
        } catch (Exception e) {
            if (et != null)
                et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return "";
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
            et = em.getTransaction();
            et.begin();
            Pfleger existing = em.find(Pfleger.class, p.getPflegerId());
            existing.setVorname(p.getVorname());
            existing.setNachname(p.getNachname());
            existing.setSvnr(p.getSvnr());
            existing.setGebDat(p.getGebDat());
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
            et = em.getTransaction();
            et.begin();
            Pfleger p = em.find(Pfleger.class, id);

            if (p != null) {
                p.getGepflegteTiere().clear();
                p.getGereinigteGehege().clear();
                p.getFuetterungsplaene().clear();
                p.getInventarListe().clear();
                p.getFuehrungen().clear();
                em.remove(p);
            }
            et.commit();
        }
        catch (Exception e){
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

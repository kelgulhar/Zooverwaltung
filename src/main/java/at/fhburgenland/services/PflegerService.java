package at.fhburgenland.services;

import at.fhburgenland.entities.Pfleger;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import at.fhburgenland.entities.*;

public class PflegerService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void run(){
        // TODO Menu und Logik für Pfleger
    }

    public static void create(Pfleger p,
                              List<Integer> tierIds,
                              List<Integer> gehegeIds,
                              List<Integer> planIds,
                              List<Integer> inventarIds,
                              List<Integer> fuehrungIds)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();

            for (Integer tid : tierIds) {
                Tier t = em.getReference(Tier.class, tid);
                p.addTier(t);
            }
            for (Integer gid : gehegeIds) {
                Gehege g = em.getReference(Gehege.class, gid);
                p.addGehege(g);
            }
            for (Integer pid : planIds) {
                Fuetterungsplan f = em.getReference(Fuetterungsplan.class, pid);
                p.addFuetterungsplan(f);
            }
            for (Integer iid : inventarIds) {
                Inventar i = em.getReference(Inventar.class, iid);
                p.addInventar(i);
            }
            for (Integer fid : fuehrungIds) {
                Fuehrung f = em.getReference(Fuehrung.class, fid);
                p.addFuehrung(f);
            }

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
            et = em.getTransaction();
            et.begin();
            Pfleger existing = em.find(Pfleger.class, p.getPflegerId());
            existing.setVorname(p.getVorname());
            existing.setNachname(p.getNachname());
            existing.setSvnr(p.getSvnr());
            existing.setGebDat(p.getGebDat());
            em.persist(existing);
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
        } catch (ConstraintViolationException cve) {
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

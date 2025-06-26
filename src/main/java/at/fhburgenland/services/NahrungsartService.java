package at.fhburgenland.services;

import at.fhburgenland.entities.*;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class NahrungsartService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Nahrungsart n){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(n);
            et.commit();
        } catch (Exception e){
            if (et != null) et.rollback();
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void createConnectionToFuetterung(int nahrungId, int fuetterungId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Nahrungsart n = null;
        Fuetterungsplan f = null;
        try {
            et = em.getTransaction();
            et.begin();

            n = em.find(Nahrungsart.class, nahrungId);
            f = em.find(Fuetterungsplan.class, fuetterungId);

            if(n != null && f != null){
                n.addFuetterungsplan(f);
                f.addNahrungsart(n);
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

    public static Nahrungsart find(int id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Nahrungsart.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            em.close();
        }
    }

    public static void update(Nahrungsart n){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction(); et.begin();
            Nahrungsart existing = em.find(Nahrungsart.class, n.getNahrungId());
            existing.setBezeichnung(n.getBezeichnung());
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
            Nahrungsart n = em.find(Nahrungsart.class, id);

            if (n != null) {
                n.getFuetterungsplaene().clear();
                em.remove(n);
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

    public static List<Nahrungsart> findAll(){
        EntityManager em = emf.createEntityManager();
        String q = "SELECT n FROM Nahrungsart n";
        TypedQuery<Nahrungsart> tq = em.createQuery(q, Nahrungsart.class);
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

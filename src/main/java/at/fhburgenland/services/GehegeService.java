package at.fhburgenland.services;

import at.fhburgenland.entities.Besucher;
import at.fhburgenland.entities.Fuehrung;
import at.fhburgenland.entities.Gehege;
import at.fhburgenland.entities.Pfleger;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class GehegeService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

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

    public static void createConnectionToPfleger(int gehegeId, int pflegerId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Gehege g = null;
        Pfleger p = null;
        try {
            et = em.getTransaction();
            et.begin();

            g = em.find(Gehege.class, gehegeId);
            p = em.find(Pfleger.class, pflegerId);

            if(p != null && g != null) {
                p.addGehege(g);
                g.addPfleger(p);
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
            if (g != null) {
                g.getTiere().clear();
                em.remove(g);
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

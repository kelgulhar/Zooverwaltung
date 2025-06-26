package at.fhburgenland.services;

import at.fhburgenland.entities.Tier;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class TierService {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Tier tier) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(tier);
            et.commit();

        } catch (Exception e){
            if (et != null){
                et.rollback();
                System.err.println(e.getMessage());
            }
        } finally {
            em.close();
        }
    }

    public static Tier find(int id) {
        EntityManager em = emf.createEntityManager();

        try{
            return em.find(Tier.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            em.close();
        }
    }

    public static void update(Tier tier) {
        // AUFPASSEN auf min max Notation von Gehege
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Tier t = null;

        try{
            et = em.getTransaction();
            et.begin();

            t = em.find(Tier.class, tier.getTierId());
            t.setTierart(tier.getTierart());
            t.setName(tier.getName());
            t.setAlter(tier.getAlter());
            t.setGehege(tier.getGehege());

            // TODO Gehegeservice.find(t.gehegeId) != null ? continue : abort
            // TODO Gehegeservice.find(tier.gehegeId) <= 10 ? continue : abort

            em.persist(t);
            et.commit();
        } catch (Exception e){
            if(et != null){
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void delete(int id) {
        // AUFPASSEN auf Gesundheitsakte und Gehege

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Tier t = null;
        // List Gesundheitsakten???

        try{
            et = em.getTransaction();
            et.begin();

            t = em.find(Tier.class, id);

            // TODO Gehegeservice.find(t.gehegeId) != null ? continue : abort
            // oder mit Gehegeservice.delete(t.gehegeId)

            // TODO Gesundheitsakteservice.findAllByTierId(tierId)
            // TODO foreach Gesundheitsservice.delete(id)

            em.remove(t);
            et.commit();
        } catch (Exception e){
            if(et != null){
                et.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public static List<Tier> findAll() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT t FROM Tier t";
        TypedQuery<Tier> tq = em.createQuery(query, Tier.class);

        try{
            return tq.getResultList();
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Tier>();
        }finally{
            em.close();
        }
    }
}
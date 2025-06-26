package at.fhburgenland.services;

import at.fhburgenland.entities.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class TierService {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    // braucht auch Pfleger
    // legt hier automatisch Gesundheitsakte mit an MUSS SOGAR
    public static void create(Tier tier, int gehegeId,
                              List<Integer> pflegerIds,
                              List<Gesundheitsakte> akten) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();

            Gehege g = em.getReference(Gehege.class, gehegeId);
            tier.setGehege(g);
            for (int pid : pflegerIds) {
                Pfleger p = em.getReference(Pfleger.class, pid);
                tier.addPfleger(p);
            }
            for (Gesundheitsakte ga : akten) {
                tier.addGesundheitsakte(ga);
                em.persist(ga);
            }

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
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Tier t = null;

        try{
            et = em.getTransaction();
            et.begin();

            t = em.find(Tier.class, id);

            if (t != null) {
                t.getPflegerListe().clear();
                t.getGesundheitsakten().clear();
                em.createQuery("DELETE FROM Gesundheitsakte g WHERE g.tier.tierId = :tid"
                ).setParameter("tid", id).executeUpdate();

                em.remove(t);
            }

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
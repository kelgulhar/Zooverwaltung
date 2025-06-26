package at.fhburgenland.services;

import at.fhburgenland.entities.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class TierService {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void create(Tier tier, int gehegeId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();

            Gehege g = em.getReference(Gehege.class, gehegeId);
            tier.setGehege(g);

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

    public static void createConnectionToPfleger(int tierId, int pflegerId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Tier t = null;
        Pfleger p = null;
        try {
            et = em.getTransaction();
            et.begin();

            t = em.find(Tier.class, tierId);
            p = em.find(Pfleger.class, p);

            if(t != null && p != null){
                t.addPfleger(p);
                p.addTier(t);
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

            //update/move tier
            tier.getGehege().addTier(tier);
            t.getGehege().removeTier(t);

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
package at.fhburgenland.services;

import at.fhburgenland.entities.Gesundheitsakte;
import at.fhburgenland.entities.Tier;
import at.fhburgenland.util.JPAUtil;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GesundheitsakteService {
    
    public static void create(Gesundheitsakte gesAkte, int tierId){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Tier t = em.getReference(Tier.class, tierId);
            t.addGesundheitsakte(gesAkte);
            em.persist(gesAkte);
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

    public static Gesundheitsakte find(int id){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try{
            return em.find(Gesundheitsakte.class, id);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            em.close();
        }
    }

    public static void update(Gesundheitsakte gAkte){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        Gesundheitsakte g = null;

        try{
            et = em.getTransaction();
            et.begin();

            g = em.find(Gesundheitsakte.class, gAkte.getAkteId());
            g.setBehandlungsart(gAkte.getBehandlungsart());
            g.setBehandlungsdatum(gAkte.getBehandlungsdatum());
            g.setTierPersistency(gAkte.getTier());

            em.persist(g);
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

    public static void delete(int id){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        Gesundheitsakte g = null;

        try{
            et = em.getTransaction();
            et.begin();

            g = em.find(Gesundheitsakte.class, id);
            if (g != null) {
                Tier t = g.getTier();
                if (t != null) {
                    t.getGesundheitsakten().remove(g);
                }
                em.remove(g);
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

    public static List<Gesundheitsakte> findALl(){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        String query = "SELECT g FROM Gesundheitsakte g";
        TypedQuery<Gesundheitsakte> tq = em.createQuery(query, Gesundheitsakte.class);

        try{
            return tq.getResultList();
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Gesundheitsakte>();
        }finally{
            em.close();
        }
    }
}

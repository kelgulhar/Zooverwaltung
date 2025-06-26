package at.fhburgenland.services;

import at.fhburgenland.entities.Gesundheitsakte;
import at.fhburgenland.entities.Tier;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GesundheitsakteService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("project");

    public static void run(){
        // TODO Menu und Logik für Gesundheitsakte
    }

    public static void create(Gesundheitsakte gesAkte, int tierId){
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();

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
        // AUFPASSEN auf min max Notation von TIer
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = null;
        Gesundheitsakte g = null;

        try{
            et = em.getTransaction();
            et.begin();

            g = em.find(Gesundheitsakte.class, gAkte.getAkteId());
            g.setBehandlungsart(gAkte.getBehandlungsart());
            g.setBehandlungsdatum(gAkte.getBehandlungsdatum());
            g.setTier(gAkte.getTier());

            // TODO selbes Drama mit min max wie bei delete

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
        // AUFPASSEN min-max Notation 1 Tier muss min 1 Akte haben?

        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
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

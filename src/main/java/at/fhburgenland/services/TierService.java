package at.fhburgenland.services;

import at.fhburgenland.entities.Tier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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

    public Tier find(int id) {
        return em.find(Tier.class, id);
    }

    public void update(Tier tier) {
        em.getTransaction().begin();
        em.merge(tier);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Tier tier = find(id);
        if (tier != null) {
            em.getTransaction().begin();
            em.remove(tier);
            em.getTransaction().commit();
        }
    }

    public List<Tier> findAll() {
        return em.createQuery("SELECT t FROM Tier t", Tier.class).getResultList();
    }
}
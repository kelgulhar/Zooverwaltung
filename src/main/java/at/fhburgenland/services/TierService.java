package services;

import entities.Tier;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TierService {

    private EntityManager em;

    public TierService(EntityManager em) {
        this.em = em;
    }

    public void create(Tier tier) {
        em.getTransaction().begin();
        em.persist(tier);
        em.getTransaction().commit();
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
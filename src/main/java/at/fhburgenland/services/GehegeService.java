package at.fhburgenland.services;

import at.fhburgenland.entities.Gehege;
import jakarta.persistence.EntityManager;
import java.util.List;

public class GehegeService {

    private EntityManager em;

    public GehegeService(EntityManager em) {
        this.em = em;
    }

    public void create(Gehege gehege) {
        em.getTransaction().begin();
        em.persist(gehege);
        em.getTransaction().commit();
    }

    public Gehege find(int id) {
        return em.find(Gehege.class, id);
    }

    public void update(Gehege gehege) {
        em.getTransaction().begin();
        em.merge(gehege);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Gehege gehege = find(id);
        if (gehege != null) {
            em.getTransaction().begin();
            em.remove(gehege);
            em.getTransaction().commit();
        }
    }

    public List<Gehege> findAll() {
        return em.createQuery("SELECT g FROM Gehege g", Gehege.class).getResultList();
    }
}
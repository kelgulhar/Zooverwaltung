package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Gehege {
    public Gehege(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gehegeId;

    @Column(nullable = false)
    private String gehegeart;

    // Gehege konnte gar nicht gelöscht werden
    // Deshalb veränderung der min max notation ein Gehege kann existieren ohne ein Tier zu beinhalten
    @OneToMany(mappedBy = "gehege")
    @Size(max=10, message = "Ein Gehege beherbergt maximal 10 Tiere")
    private List<Tier> tiere;

    @ManyToMany(mappedBy = "gereinigteGehege")
    private List<Pfleger> pflegerReinigung;

    // Getter und Setter

    public int getGehegeId() {
        return gehegeId;
    }

    public String getGehegeart() {
        return gehegeart;
    }

    public void setGehegeart(String gehegeart) {
        this.gehegeart = gehegeart;
    }

    public List<Tier> getTiere() {
        return tiere;
    }

    public void setTiere(List<Tier> tiere) {
        this.tiere = tiere;
    }

    public List<Pfleger> getPflegerReinigung() {
        return pflegerReinigung;
    }

    public void setPflegerReinigung(List<Pfleger> pflegerReinigung) {
        this.pflegerReinigung = pflegerReinigung;
    }

    // Helper
    @PreRemove
    private void preventRemoveIfTiereExist() {
        if (!tiere.isEmpty()) {
            throw new IllegalStateException(
                    "Kann Gehege nicht löschen: " + tiere.size() + " Tier(e) sind noch zugeordnet."
            );
        }
    }

    public void addTier(Tier t) {
        if (!tiere.contains(t)) {
            tiere.add(t);
            t.setGehege(this);
        }
    }

    public void removeTier(Tier t) {
        if (tiere.remove(t)) {
            t.setGehege(null);
        }
    }

    public void addPfleger(Pfleger p) {
        if (!pflegerReinigung.contains(p)) {
            pflegerReinigung.add(p);
            p.getGereinigteGehege().add(this);
        }
    }

    public void removePfleger(Pfleger p) {
        if (pflegerReinigung.remove(p)) {
            p.getGereinigteGehege().remove(this);
        }
    }

}
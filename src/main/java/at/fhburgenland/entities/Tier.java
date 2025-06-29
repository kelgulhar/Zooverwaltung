package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.lang.reflect.Type;
import java.util.List;

@Entity(name = "Tier")
@Table(name = "tier")
public class Tier {

    public Tier() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tier_ID", updatable = false, nullable = false)
    private int tierId;

    @Column(name = "Tierart", length = 32)
    private String tierart;

    @Column(name = "Name", nullable = false, length = 32)
    private String name;

    @Column(name = "Alter")
    private int alter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Gehege_ID", nullable = false)
    private Gehege gehege;

    @OneToMany(mappedBy = "tier")
    private List<Gesundheitsakte> gesundheitsakten;

    @ManyToMany(mappedBy = "gepflegteTiere")
    @Size(max = 3, message = "Ein Tier wird von maximal 3 Pfleger gepflegt")
    private List<Pfleger> pflegerListe;

    // Getter und Setter
    public int getTierId() {
        return tierId;
    }

    public String getTierart() {
        return tierart;
    }

    public void setTierart(String tierart) {
        this.tierart = tierart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public Gehege getGehege() {
        return gehege;
    }

    public void setGehege(Gehege gehege) {
        this.gehege = gehege;
    }

    public List<Gesundheitsakte> getGesundheitsakten() {
        return gesundheitsakten;
    }

    public void setGesundheitsakten(List<Gesundheitsakte> gesundheitsakten) {
        this.gesundheitsakten = gesundheitsakten;
    }

    public List<Pfleger> getPflegerListe() {
        return pflegerListe;
    }

    public void setPflegerListe(List<Pfleger> pflegerListe) {
        this.pflegerListe = pflegerListe;
    }

    // Helper
    public void addGesundheitsakte(Gesundheitsakte ga) {
        if (!gesundheitsakten.contains(ga)) {
            gesundheitsakten.add(ga);
            ga.setTier(this);
        }
    }

    public void removeGesundheitsakte(Gesundheitsakte ga) {
        if (gesundheitsakten.remove(ga)) {
            ga.setTier(null);
        }
    }

    public void addPfleger(Pfleger p) {
        if (!pflegerListe.contains(p)) {
            pflegerListe.add(p);
            p.getGepflegteTiere().add(this);
        }
    }

    public void removePfleger(Pfleger p) {
        if (pflegerListe.remove(p)) {
            p.getGepflegteTiere().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Tier{" +
                "tierId=" + tierId +
                ", tierart='" + tierart + '\'' +
                ", name='" + name + '\'' +
                ", alter=" + alter +
                ", gehege=" + gehege +
                '}';
    }
}
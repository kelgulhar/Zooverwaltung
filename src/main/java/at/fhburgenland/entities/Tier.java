package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tier_id", updatable = false, nullable = false)
    private int tierId;

    @Column(name="tierart", length = 32)
    private String tierart;

    @Column(name="name", nullable = false, length = 32)
    private String name;

    @Column(name="alter")
    private int alter;

    @ManyToOne
    @JoinColumn(name = "gehege_id", nullable = false)
    private Gehege gehege;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL) // TODO Cascade selbst implementieren
    private List<Gesundheitsakte> gesundheitsakten;

    @ManyToMany(mappedBy = "gepflegteTiere")
    private List<Pfleger> pflegerListe;

    // Getter und Setter
    public int getTierId(){
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
}
package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Gehege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gehegeId;

    @Column(nullable = false)
    private String gehegeart;

    @OneToMany(mappedBy = "gehege", cascade = CascadeType.ALL) // TODO Cascade selbst implementieren
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
}
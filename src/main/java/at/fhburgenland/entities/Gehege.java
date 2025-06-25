package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Gehege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gehegeId;

    @Column(nullable = false)
    private String gehegeart;

    @OneToMany(mappedBy = "gehege", cascade = CascadeType.ALL) // TODO Cascade selbst implementieren
    @Size(min=1, max=10, message = "Ein Gehege beherbergt mindestens 1 und maximal 10 Tiere")
    private List<Tier> tiere;

    @ManyToMany(mappedBy = "gereinigteGehege")
    @Size(min = 1, message = "Gehege muss von mindestens einem Pfleger gereinigt werden")
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
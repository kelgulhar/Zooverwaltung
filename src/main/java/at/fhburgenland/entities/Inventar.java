package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Inventar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventarId;

    @Column(nullable = false)
    private String bezeichnung;

    @ManyToMany(mappedBy = "inventarListe")
    @Size(min=1, message = "Ein Inventaritem muss von mindestens einem Pfleger verwaltet werden")
    private List<Pfleger> verwalter;

    // Getter und Setter

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public List<Pfleger> getVerwalter() {
        return verwalter;
    }

    public void setVerwalter(List<Pfleger> verwalter) {
        this.verwalter = verwalter;
    }

    public int getInventarId() {
        return inventarId;
    }

    // Helper Methoden
    // --- Inventar.java (M:N zu Pfleger) ---
    public void addPfleger(Pfleger p) {
        if (!verwalter.contains(p)) {
            verwalter.add(p);
            p.getInventarListe().add(this);
        }
    }

    public void removePfleger(Pfleger p) {
        if (verwalter.remove(p)) {
            p.getInventarListe().remove(this);
        }
    }

}
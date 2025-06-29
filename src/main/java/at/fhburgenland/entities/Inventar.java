package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity(name = "Inventar")
@Table(name = "inventar")
public class Inventar {

    public Inventar() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Inventar_ID")
    private int inventarId;

    @Column(name = "Bezeichnung", nullable = false)
    private String bezeichnung;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "inventarListe")
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

    @Override
    public String toString() {
        return "Inventar{" +
                "inventarId=" + inventarId +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", verwalter=" + verwalter +
                '}';
    }
}
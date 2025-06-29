package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Nahrungsart")
@Table(name = "nahrungsart")
public class Nahrungsart {

    public Nahrungsart() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Nahrung_ID")
    private int nahrungId;

    @Column(name = "Bezeichnung", nullable = false)
    private String bezeichnung;

    @ManyToMany(mappedBy = "nahrungsarten")
    private List<Fuetterungsplan> fuetterungsplaene;    // n Nahrungsarten können in n Führungen vorkommen


    // Getter und Setter

    public int getNahrungId() {
        return this.nahrungId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public List<Fuetterungsplan> getFuetterungsplaene() {
        return fuetterungsplaene;
    }

    public void setFuetterungsplaene(List<Fuetterungsplan> fuetterungsplaene) {
        this.fuetterungsplaene = fuetterungsplaene;
    }

    // Helper Methoden
    public void addFuetterungsplan(Fuetterungsplan f) {
        if (!fuetterungsplaene.contains(f)) {
            fuetterungsplaene.add(f);
            f.getNahrungsarten().add(this);
        }
    }

    public void removeFuetterungsplan(Fuetterungsplan f) {
        if (fuetterungsplaene.remove(f)) {
            f.getNahrungsarten().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Nahrungsart{" +
                "nahrungId=" + nahrungId +
                ", bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
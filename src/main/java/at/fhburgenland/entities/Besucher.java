package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity(name="Besucher")
@Table(name="besucher")
public class Besucher {

    public Besucher(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="besucher_id", updatable = false, nullable = false)
    private int besucherId;

    @Column(name="vorname",nullable = false)
    private String vorname;

    @Column(name="nachname",nullable = false)
    private String nachname;

    @ManyToMany
    @JoinTable(name = "Besucht",
        joinColumns = @JoinColumn(name = "besucher_id"),
        inverseJoinColumns = @JoinColumn(name = "fuehrung_id"))
    @Size(max=3, message="Ein Besucher kann maximal 3 Führungen besuchen")
    private List<Fuehrung> besuchteFuehrungen;

    // Getter und Setter

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public int getBesucherId() {
        return besucherId;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public List<Fuehrung> getBesuchteFuehrungen() {
        return besuchteFuehrungen;
    }

    public void setBesuchteFuehrungen(List<Fuehrung> besuchteFuehrungen) {
        this.besuchteFuehrungen = besuchteFuehrungen;
    }

    // Helper Methoden
    public void addFuehrung(Fuehrung f) {
        if (!besuchteFuehrungen.contains(f)) {
            besuchteFuehrungen.add(f);
            f.getBesucherListe().add(this);
        }
    }

    public void removeFuehrung(Fuehrung f) {
        if (besuchteFuehrungen.remove(f)) {
            f.getBesucherListe().remove(this);
        }
    }
}
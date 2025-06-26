package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Fuehrung {

    public Fuehrung(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fuehrungId;

    @Column(nullable = false)
    private String gehegeroute;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(nullable = false)
    private LocalTime uhrzeit;

    @ManyToMany(mappedBy = "fuehrungen")
    @Size(max=1, message = "Eine Führung kann nur von einem Pfleger veranstaltet werden")
    private List<Pfleger> veranstalter;

    @ManyToMany(mappedBy = "besuchteFuehrungen")
    @Size(max=20, message = "Eine FÜhrung muss von mindestens 1 und maximal 20 Besuchern besucht werden")
    private List<Besucher> besucherListe;

    // Getter und Setter

    public int getFuehrungId() {
        return fuehrungId;
    }

    public String getGehegeroute() {
        return gehegeroute;
    }

    public void setGehegeroute(String gehegeroute) {
        this.gehegeroute = gehegeroute;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(LocalTime uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public List<Pfleger> getVeranstalter() {
        return veranstalter;
    }

    public void setVeranstalter(List<Pfleger> veranstalter) {
        this.veranstalter = veranstalter;
    }

    public List<Besucher> getBesucherListe() {
        return besucherListe;
    }

    public void setBesucherListe(List<Besucher> besucherListe) {
        this.besucherListe = besucherListe;
    }

    // Helper Klassen
    public void addPfleger(Pfleger p) {
        if (!veranstalter.contains(p)) {
            veranstalter.add(p);
            p.getFuehrungen().add(this);
        }
    }

    public void removePfleger(Pfleger p) {
        if (veranstalter.remove(p)) {
            p.getFuehrungen().remove(this);
        }
    }

    public void addBesucher(Besucher b) {
        if (!besucherListe.contains(b)) {
            besucherListe.add(b);
            b.getBesuchteFuehrungen().add(this);
        }
    }

    public void removeBesucher(Besucher b) {
        if (besucherListe.remove(b)) {
            b.getBesuchteFuehrungen().remove(this);
        }
    }

}
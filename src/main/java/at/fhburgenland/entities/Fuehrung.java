package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Fuehrung {

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
    private List<Pfleger> veranstalter;

    @ManyToMany(mappedBy = "besuchteFuehrungen")
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
}
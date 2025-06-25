package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "Fuetterungsplan")
@Table(name = "fuetterungsplan")
public class Fuetterungsplan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id", updatable = false, nullable = false)
    private int planId;

    @Column(nullable = false)
    private LocalTime uhrzeit;

    @Column(nullable = false)
    private LocalDate datum;

    @ManyToMany(mappedBy = "fuetterungsplaene")
    private List<Pfleger> pflegerListe;

    @ManyToMany
    @JoinTable(name = "Fuetterungsplan_Nahrungsart",
        joinColumns = @JoinColumn(name = "plan_id"),
        inverseJoinColumns = @JoinColumn(name = "nahrung_id"))
    private List<Nahrungsart> nahrungsarten;

    // Getter und Setter

    public int getPlanId() {
        return planId;
    }

    public LocalTime getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(LocalTime uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public List<Pfleger> getPflegerListe() {
        return pflegerListe;
    }

    public void setPflegerListe(List<Pfleger> pflegerListe) {
        this.pflegerListe = pflegerListe;
    }

    public List<Nahrungsart> getNahrungsarten() {
        return nahrungsarten;
    }

    public void setNahrungsarten(List<Nahrungsart> nahrungsarten) {
        this.nahrungsarten = nahrungsarten;
    }
}
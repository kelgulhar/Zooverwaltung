package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "Fuetterungsplan")
@Table(name = "fuetterungsplan")
public class Fuetterungsplan {

    public Fuetterungsplan() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Plan_ID", updatable = false, nullable = false)
    private int planId;

    @Column(name = "Uhrzeit", nullable = false)
    private LocalTime uhrzeit;

    @Column(name = "Datum", nullable = false)
    private LocalDate datum;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "fuetterungsplaene")
    private List<Pfleger> pflegerListe;

    @ManyToMany(fetch = FetchType.EAGER)
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

    // Helper Methoden
    public void addNahrungsart(Nahrungsart n) {
        if (!nahrungsarten.contains(n)) {
            nahrungsarten.add(n);
            n.getFuetterungsplaene().add(this);
        }
    }

    public void removeNahrungsart(Nahrungsart n) {
        if (nahrungsarten.remove(n)) {
            n.getFuetterungsplaene().remove(this);
        }
    }

    public void addPfleger(Pfleger p) {
        if (!pflegerListe.contains(p)) {
            pflegerListe.add(p);
            p.getFuetterungsplaene().add(this);
        }
    }

    public void removePfleger(Pfleger p) {
        if (pflegerListe.remove(p)) {
            p.getFuetterungsplaene().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Fuetterungsplan{" +
                "planId=" + planId +
                ", uhrzeit=" + uhrzeit +
                ", datum=" + datum +
                ", pflegerListe=" + pflegerListe +
                ", nahrungsarten=" + nahrungsarten +
                '}';
    }
}
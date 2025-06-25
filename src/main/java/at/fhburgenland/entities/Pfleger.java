package at.fhburgenland.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Pfleger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pflegerId;

    @Column(nullable = false)
    private String vorname;

    @Column(nullable = false)
    private String nachname;

    @Column(length = 4, nullable = false)
    private String svnr;

    @Column()
    private LocalDate gebDat;

    @ManyToMany
    @JoinTable(name = "Pflegt",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "tier_id"))
    @Size(max=10, message = "Ein Pfleger kann maximal 10 Tiere pflegen")
    private List<Tier> gepflegteTiere;

    @ManyToMany
    @JoinTable(name = "Reinigt",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "gehege_id"))
    @Size(max=5, message = "Ein Pfleger kann maximal 5 Gehege reinigen")
    private List<Gehege> gereinigteGehege;

    @ManyToMany
    @JoinTable(name = "Verwaltet",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "inventar_id"))
    private List<Inventar> inventarListe;

    @ManyToMany
    @JoinTable(name = "Fuehrt_durch",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "plan_id"))
    @Size(max=5, message = "Ein Pfleger kann maximal 5 Fütterungen durchführen")
    private List<Fuetterungsplan> fuetterungsplaene;

    @ManyToMany
    @JoinTable(name = "Veranstaltet",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "fuehrung_id"))
    @Size(max=5, message = "Ein Pfleger kann maximal 5 Führungen veranstalten")
    private List<Fuehrung> fuehrungen;

    // Getter und Setter

    public int getPflegerId() {
        return pflegerId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getSvnr() {
        return svnr;
    }

    public void setSvnr(String svnr) {
        this.svnr = svnr;
    }

    public LocalDate getGebDat() {
        return gebDat;
    }

    public void setGebDat(LocalDate gebDat) {
        this.gebDat = gebDat;
    }

    public List<Tier> getGepflegteTiere() {
        return gepflegteTiere;
    }

    public void setGepflegteTiere(List<Tier> gepflegteTiere) {
        this.gepflegteTiere = gepflegteTiere;
    }

    public List<Gehege> getGereinigteGehege() {
        return gereinigteGehege;
    }

    public void setGereinigteGehege(List<Gehege> gereinigteGehege) {
        this.gereinigteGehege = gereinigteGehege;
    }

    public List<Inventar> getInventarListe() {
        return inventarListe;
    }

    public void setInventarListe(List<Inventar> inventarListe) {
        this.inventarListe = inventarListe;
    }

    public List<Fuetterungsplan> getFuetterungsplaene() {
        return fuetterungsplaene;
    }

    public void setFuetterungsplaene(List<Fuetterungsplan> fuetterungsplaene) {
        this.fuetterungsplaene = fuetterungsplaene;
    }

    public List<Fuehrung> getFuehrungen() {
        return fuehrungen;
    }

    public void setFuehrungen(List<Fuehrung> fuehrungen) {
        this.fuehrungen = fuehrungen;
    }
}
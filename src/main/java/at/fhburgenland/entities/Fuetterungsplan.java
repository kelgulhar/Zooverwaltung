package entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Fuetterungsplan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planId;

    @Temporal(TemporalType.TIME)
    private Date uhrzeit;

    @Temporal(TemporalType.DATE)
    private Date datum;

    @ManyToMany(mappedBy = "fuetterungsplaene")
    private List<Pfleger> pflegerListe;

    @ManyToMany
    @JoinTable(name = "Fuetterungsplan_Nahrungsart",
        joinColumns = @JoinColumn(name = "plan_id"),
        inverseJoinColumns = @JoinColumn(name = "nahrung_id"))
    private List<Nahrungsart> nahrungsarten;

    // Getter und Setter
}
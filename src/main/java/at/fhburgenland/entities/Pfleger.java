package entities;

import jakarta.persistence.*;
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

    @ManyToMany
    @JoinTable(name = "Pflegt",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "tier_id"))
    private List<Tier> gepflegteTiere;

    @ManyToMany
    @JoinTable(name = "Reinigt",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "gehege_id"))
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
    private List<Fuetterungsplan> fuetterungsplaene;

    @ManyToMany
    @JoinTable(name = "Veranstaltet",
        joinColumns = @JoinColumn(name = "pfleger_id"),
        inverseJoinColumns = @JoinColumn(name = "fuehrung_id"))
    private List<Fuehrung> fuehrungen;

    // Getter und Setter
}
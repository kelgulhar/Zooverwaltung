package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tier_id", updatable = false, nullable = false)
    private int tierId;

    @Column(name="tierart", length = 32)
    private String tierart;

    @Column(name="name", nullable = false, length = 32)
    private String name;

    @Column(name="alter")
    private int alter;

    @ManyToOne
    @JoinColumn(name = "gehege_id", nullable = false)
    private Gehege gehege;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL)
    private List<Gesundheitsakte> gesundheitsakten;

    @ManyToMany(mappedBy = "gepflegteTiere")
    private List<Pfleger> pflegerListe;

}
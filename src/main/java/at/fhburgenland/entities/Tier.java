package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tierId;

    private String tierart;

    @Column(nullable = false)
    private String name;

    private int alter;

    @ManyToOne
    @JoinColumn(name = "gehege_id", nullable = false)
    private Gehege gehege;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL)
    private List<Gesundheitsakte> gesundheitsakten;

    @ManyToMany(mappedBy = "gepflegteTiere")
    private List<Pfleger> pflegerListe;

    // Getter und Setter
}
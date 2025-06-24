package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity(name="Besucher")
@Table(name="besucher")
public class Besucher {

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
    private List<Fuehrung> besuchteFuehrungen;

    // Getter und Setter
}
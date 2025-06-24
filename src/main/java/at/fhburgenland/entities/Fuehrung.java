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
}
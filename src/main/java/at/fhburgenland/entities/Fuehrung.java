package entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Fuehrung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fuehrungId;

    @Column(nullable = false)
    private String gehegeroute;

    @Temporal(TemporalType.DATE)
    private Date datum;

    @Temporal(TemporalType.TIME)
    private Date uhrzeit;

    @ManyToMany(mappedBy = "fuehrungen")
    private List<Pfleger> veranstalter;

    @ManyToMany(mappedBy = "besuchteFuehrungen")
    private List<Besucher> besucherListe;

    // Getter und Setter
}
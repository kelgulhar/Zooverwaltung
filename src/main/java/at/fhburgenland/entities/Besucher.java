package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Besucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int besucherId;

    @Column(nullable = false)
    private String vorname;

    @Column(nullable = false)
    private String nachname;

    @ManyToMany
    @JoinTable(name = "Besucht",
        joinColumns = @JoinColumn(name = "besucher_id"),
        inverseJoinColumns = @JoinColumn(name = "fuehrung_id"))
    private List<Fuehrung> besuchteFuehrungen;

    // Getter und Setter
}
package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Nahrungsart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nahrungId;

    @Column(nullable = false)
    private String bezeichnung;

    @ManyToMany(mappedBy = "nahrungsarten")
    private List<Fuetterungsplan> fuetterungsplaene;

    // Getter und Setter
}
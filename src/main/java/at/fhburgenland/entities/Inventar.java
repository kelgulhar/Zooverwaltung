package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Inventar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventarId;

    @Column(nullable = false)
    private String bezeichnung;

    @ManyToMany(mappedBy = "inventarListe")
    private List<Pfleger> verwalter;

    // Getter und Setter
}
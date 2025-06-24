package at.fhburgenland.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Gehege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gehegeId;

    @Column(nullable = false)
    private String gehegeart;

    @OneToMany(mappedBy = "gehege", cascade = CascadeType.ALL)
    private List<Tier> tiere;

    @ManyToMany(mappedBy = "gereinigteGehege")
    private List<Pfleger> pflegerReinigung;

    // Getter und Setter
}
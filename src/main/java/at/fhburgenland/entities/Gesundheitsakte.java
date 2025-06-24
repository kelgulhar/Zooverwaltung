package entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Gesundheitsakte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int akteId;

    @Column(nullable = false)
    private String behandlungsart;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date behandlungsdatum;

    @ManyToOne
    @JoinColumn(name = "tier_id", nullable = false)
    private Tier tier;

    // Getter und Setter
}
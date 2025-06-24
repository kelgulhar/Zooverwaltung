package at.fhburgenland.entities;

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
    public int getAkteId(){
        return this.akteId;
    }

    public String getBehandlungsart() {
        return behandlungsart;
    }

    public void setBehandlungsart(String behandlungsart) {
        this.behandlungsart = behandlungsart;
    }

    public Date getBehandlungsdatum() {
        return behandlungsdatum;
    }

    public void setBehandlungsdatum(Date behandlungsdatum) {
        this.behandlungsdatum = behandlungsdatum;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }
}
package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Gesundheitsakte {

    public Gesundheitsakte(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int akteId;

    @Column(nullable = false)
    private String behandlungsart;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate behandlungsdatum;

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

    public LocalDate getBehandlungsdatum() {
        return behandlungsdatum;
    }

    public void setBehandlungsdatum(LocalDate behandlungsdatum) {
        this.behandlungsdatum = behandlungsdatum;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    // Helper Methoden
    public void setTierPersistency(Tier t) {
        this.tier = t;
        if (t != null && !t.getGesundheitsakten().contains(this)) {
            t.getGesundheitsakten().add(this);
        }
    }

}
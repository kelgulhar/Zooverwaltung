package at.fhburgenland.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Gesundheitsakte")
@Table(name = "gesundheitsakte")
public class Gesundheitsakte {

    public Gesundheitsakte() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Akte_ID")
    private int akteId;

    @Column(name = "Behandlungsart", nullable = false)
    private String behandlungsart;

    @Column(name = "Behandlungsdatum", nullable = false)
    private LocalDate behandlungsdatum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tier_id", nullable = false)
    private Tier tier;

    // Getter und Setter
    public int getAkteId() {
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

    @Override
    public String toString() {
        return "Gesundheitsakte{" +
                "akteId=" + akteId +
                ", behandlungsart='" + behandlungsart + '\'' +
                ", behandlungsdatum=" + behandlungsdatum +
                ", tier=" + tier +
                '}';
    }
}
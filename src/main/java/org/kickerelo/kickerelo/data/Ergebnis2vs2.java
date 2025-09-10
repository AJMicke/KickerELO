package org.kickerelo.kickerelo.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Ergebnis2vs2 {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "GEWINNER_VORN", nullable = false)
    private Spieler gewinnerVorn;

    @ManyToOne
    @JoinColumn(name = "GEWINNER_HINTEN", nullable = false)
    private Spieler gewinnerHinten;

    @ManyToOne
    @JoinColumn(name = "VERLIERER_VORN", nullable = false)
    private Spieler verliererVorn;

    @ManyToOne
    @JoinColumn(name = "VERLIERER_HINTEN", nullable = false)
    private Spieler verliererHinten;

    @Column(name = "TORE_VERLIERER", nullable = false)
    private short toreVerlierer;

    @Column(name = "ZEITPUNKT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBMITTER", referencedColumnName = "ID")
    private AuthentikUser submitter;

    public Ergebnis2vs2() {
    }

    public Ergebnis2vs2(Spieler gewinnerVorn, Spieler gewinnerHinten, Spieler verliererVorn, Spieler verliererHinten, short toreVerlierer, AuthentikUser submitter) {
        this.gewinnerVorn = gewinnerVorn;
        this.gewinnerHinten = gewinnerHinten;
        this.verliererVorn = verliererVorn;
        this.verliererHinten = verliererHinten;
        this.toreVerlierer = toreVerlierer;
        this.submitter = submitter;
    }

    public long getId() {
        return id;
    }

    public short getToreVerlierer() {
        return toreVerlierer;
    }

    public void setToreVerlierer(short toreVerlierer) {
        this.toreVerlierer = toreVerlierer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Spieler getVerliererHinten() {
        return verliererHinten;
    }

    public void setVerliererHinten(Spieler verliererHinten) {
        this.verliererHinten = verliererHinten;
    }

    public Spieler getVerliererVorn() {
        return verliererVorn;
    }

    public void setVerliererVorn(Spieler verliererVorn) {
        this.verliererVorn = verliererVorn;
    }

    public Spieler getGewinnerHinten() {
        return gewinnerHinten;
    }

    public void setGewinnerHinten(Spieler gewinnerHinten) {
        this.gewinnerHinten = gewinnerHinten;
    }

    public Spieler getGewinnerVorn() {
        return gewinnerVorn;
    }

    public void setGewinnerVorn(Spieler gewinnerVorn) {
        this.gewinnerVorn = gewinnerVorn;
    }
}

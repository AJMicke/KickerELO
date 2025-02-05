package org.kickerelo.kickerelo.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Ergebnis1vs1 {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "GEWINNER", nullable = false)
    private Spieler gewinner;

    @ManyToOne
    @JoinColumn(name = "VERLIERER", nullable = false)
    private Spieler verlierer;

    @Column(name = "TORE_VERLIERER", nullable = false)
    private short toreVerlierer;

    @Column(name = "ZEITPUNKT", updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    public Ergebnis1vs1() {
    }

    public Ergebnis1vs1(Spieler gewinner, Spieler verlierer, short toreVerlierer) {
        this.gewinner = gewinner;
        this.verlierer = verlierer;
        this.toreVerlierer = toreVerlierer;
    }

    public Spieler getVerlierer() {
        return verlierer;
    }

    public void setVerlierer(Spieler verlierer) {
        this.verlierer = verlierer;
    }

    public Spieler getGewinner() {
        return gewinner;
    }

    public void setGewinner(Spieler gewinner) {
        this.gewinner = gewinner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

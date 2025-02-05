package org.kickerelo.kickerelo.data;

import jakarta.persistence.*;

@Entity
@Table(name = "SPIELER")
public class Spieler {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ELO", nullable = false)
    private float elo;

    @Column(name = "ELO_ALT")
    private float elo_alt;

    public Spieler() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getElo() {
        return elo;
    }

    public void setElo(float elo) {
        this.elo = elo;
    }

    public float getElo_alt() {
        return elo_alt;
    }

    public void setElo_alt(float elo_alt) {
        this.elo_alt = elo_alt;
    }
}

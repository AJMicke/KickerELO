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
}

package org.kickerelo.kickerelo.data;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.util.Optional;

@Entity
@Table(name = "SPIELER")
public class Spieler {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ELO1vs1", nullable = false)
    private float elo1vs1;

    @Column(name = "ELO2vs2", nullable = false)
    private float elo2vs2;

    @Column(name = "ELO_ALT")
    private float elo_alt;

    @OneToOne(optional = true)
    @JoinColumn(name = "AUTHENTIK_USER", referencedColumnName = "ID", unique = true)
    private AuthentikUser authentikUser;

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

    public float getElo1vs1() {
        return elo1vs1;
    }

    public void setElo1vs1(float elo) {
        this.elo1vs1 = elo;
    }

    public float getElo2vs2() {
        return elo2vs2;
    }

    public void setElo2vs2(float elo2vs2) {
        this.elo2vs2 = elo2vs2;
    }

    public float getElo_alt() {
        return elo_alt;
    }

    public void setElo_alt(float elo_alt) {
        this.elo_alt = elo_alt;
    }

    public Optional<AuthentikUser> getAuthentikUser() {
        return Optional.ofNullable(authentikUser);
    }

    public void setAuthentikUser(@Nullable AuthentikUser authentikUser) {
        this.authentikUser = authentikUser;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Spieler)) return false;
        return this.id == ((Spieler) o).id;
    }
}

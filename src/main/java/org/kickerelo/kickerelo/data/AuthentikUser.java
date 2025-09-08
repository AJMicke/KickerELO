package org.kickerelo.kickerelo.data;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.util.Optional;

@Entity
@Table(name = "AUTHENTIK_USER")
public class AuthentikUser {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @OneToOne(mappedBy = "authentikUser", optional = true)
    private Spieler spieler;

    @Column(name = "NAME", nullable = false)
    private String name;

    public AuthentikUser() {
    }

    public AuthentikUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Optional<Spieler> getSpieler() {
        return Optional.ofNullable(spieler);
    }

    public void setSpieler(@Nullable Spieler spieler) {
        this.spieler = spieler;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthentikUser)) return false;
        return this.id == ((AuthentikUser) o).id;
    }
}

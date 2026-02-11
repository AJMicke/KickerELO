package org.kickerelo.kickerelo.data;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "submitter", cascade = CascadeType.ALL)
    private List<Ergebnis1vs1> submittedGames1v1;

    @OneToMany(mappedBy = "submitter", cascade = CascadeType.ALL)
    private List<Ergebnis2vs2> submittedGames2v2;

    public AuthentikUser() {
    }

    public AuthentikUser(String id, String name) {
        this.id = id;
        this.name = name;
        this.submittedGames1v1 = new ArrayList<>();
        this.submittedGames2v2 = new ArrayList<>();
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

    public void addSubmittedResult(Ergebnis1vs1 ergebnis) {
        submittedGames1v1.add(ergebnis);
    }

    public void addSubmittedResult(Ergebnis2vs2 ergebnis) {
        submittedGames2v2.add(ergebnis);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthentikUser)) return false;
        return this.id == ((AuthentikUser) o).id;
    }
}

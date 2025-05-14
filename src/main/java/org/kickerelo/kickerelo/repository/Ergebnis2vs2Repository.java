package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ergebnis2vs2Repository extends JpaRepository<Ergebnis2vs2, Long> {
    int countByGewinnerHinten(Spieler gewinnerHinten);
    int countByGewinnerVorn(Spieler gewinnerVon);
    int countByVerliererHinten(Spieler verliererHinten);
    int countByVerliererVorn(Spieler verlierVon);

    int countByGewinnerVornOrGewinnerHinten(Spieler gewinnerVon, Spieler gewinnerHinten);
    int countByGewinnerVornOrGewinnerHintenOrVerliererVornOrVerliererHinten(Spieler gewinnerVon, Spieler gewinnerHinten,
                                                                            Spieler verliererVon, Spieler verliererHinten);

    int countByGewinnerVornOrVerliererVorn(Spieler gewinnerVon, Spieler verliererHinten);
}

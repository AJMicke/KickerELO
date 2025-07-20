package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Ergebnis2vs2Repository extends JpaRepository<Ergebnis2vs2, Long> {
    int countByGewinnerHinten(Spieler gewinnerHinten);
    int countByGewinnerVorn(Spieler gewinnerVon);
    int countByVerliererHinten(Spieler verliererHinten);
    int countByVerliererVorn(Spieler verlierVon);
    int countByGewinnerVornOrGewinnerHinten(Spieler gewinnerVon, Spieler gewinnerHinten);
    int countByVerliererVornOrVerliererHinten(Spieler verliererVon, Spieler verliererHinten);

    int countByGewinnerVornOrGewinnerHintenOrVerliererVornOrVerliererHinten(Spieler gewinnerVon, Spieler gewinnerHinten,
                                                                            Spieler verliererVon, Spieler verliererHinten);

    int countByGewinnerVornOrVerliererVorn(Spieler gewinnerVon, Spieler verliererHinten);

    @Query("select avg(case when e.verliererVorn = :s then (e.toreVerlierer - 10) when e.gewinnerVorn = :s then (10 - e.toreVerlierer) end) from Ergebnis2vs2 e where e.gewinnerVorn = :s or e.verliererVorn = :s")
    Float avgGoalDiffFront(@Param("s") Spieler s);

    @Query("select avg(case when e.verliererHinten = :s then (e.toreVerlierer - 10) when e.gewinnerHinten = :s then (10 - e.toreVerlierer) end) from Ergebnis2vs2 e where e.gewinnerHinten = :s or e.verliererHinten = :s")
    Float avgGoalDiffBack(@Param("s") Spieler s);

    @Query("select e from Ergebnis2vs2 e where (:s = e.verliererHinten or :s = e.verliererVorn or :s = e.gewinnerHinten or :s = e.gewinnerVorn) order by e.timestamp desc")
    List<Ergebnis2vs2> getResultsForSpieler(@Param("s") Spieler s);
}

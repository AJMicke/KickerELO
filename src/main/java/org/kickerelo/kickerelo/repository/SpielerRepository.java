package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {
    Optional<Spieler> findByName(String name);

    @Query("SELECT s FROM Spieler s WHERE EXISTS (SELECT 1 FROM Ergebnis1vs1 e WHERE e.gewinner=s OR e.verlierer=s)")
    List<Spieler> getSpielerWith1vs1Games();

    @Query("SELECT s FROM Spieler s WHERE EXISTS (SELECT 1 FROM Ergebnis2vs2 e " +
            "WHERE e.gewinnerVorn=s OR e.verliererVorn=s OR e.gewinnerHinten=s OR e.verliererHinten=s)")
    List<Spieler> getSpielerWith2vs2Games();
}

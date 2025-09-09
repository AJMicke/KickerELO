package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {
    Optional<Spieler> findByName(String name);

    @Query("SELECT s FROM Spieler s WHERE EXISTS (SELECT 1 FROM Ergebnis1vs1 e WHERE e.gewinner=s OR e.verlierer=s) " +
            "order by s.elo1vs1 desc ")
    List<Spieler> getSpielerWith1vs1Games();

    @Query("SELECT s FROM Spieler s WHERE EXISTS (SELECT 1 FROM Ergebnis2vs2 e " +
            "WHERE e.gewinnerVorn=s OR e.verliererVorn=s OR e.gewinnerHinten=s OR e.verliererHinten=s) " +
            "order by s.elo2vs2 desc")
    List<Spieler> getSpielerWith2vs2Games();

    @Query("select distinct s from Spieler s join Ergebnis1vs1 e on (s = e.verlierer or s = e.gewinner) " +
            "where e.timestamp >= :dateTime " +
            "order by s.elo1vs1 desc")
    List<Spieler> getSpielerWith1vs1GamesSince(@Param("dateTime") LocalDateTime dateTime);

    @Query("select distinct s from Spieler s join Ergebnis2vs2 e " +
            "on (s = e.verliererVorn or s = e.gewinnerVorn or s = e.verliererHinten or s = e.gewinnerHinten) " +
            "where e.timestamp >= :dateTime " +
            "order by s.elo2vs2 desc")
    List<Spieler> getSpielerWith2vs2GamesSince(@Param("dateTime") LocalDateTime dateTime);
}

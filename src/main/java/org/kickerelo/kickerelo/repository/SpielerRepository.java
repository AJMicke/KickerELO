package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Spieler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpielerRepository extends JpaRepository<Spieler, Long> {
    Optional<Spieler> findByName(String name);
}

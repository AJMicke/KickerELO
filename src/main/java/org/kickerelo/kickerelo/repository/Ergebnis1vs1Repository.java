package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ergebnis1vs1Repository extends JpaRepository<Ergebnis1vs1, Long> {
}

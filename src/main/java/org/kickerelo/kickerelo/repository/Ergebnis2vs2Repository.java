package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface Ergebnis2vs2Repository extends JpaRepository<Ergebnis2vs2, Long> {
}

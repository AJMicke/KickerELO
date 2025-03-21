package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Ergebnis1vs1Repository extends JpaRepository<Ergebnis1vs1, Long> {
    @Query("""
        SELECT e from Ergebnis1vs1 e WHERE
        (:S1 IS NOT NULL AND :S2 IS NOT NULL AND (:S1=e.gewinner.name AND :S2 = e.verlierer.name OR :S1=e.verlierer.name AND :S2=e.gewinner.name)) OR
        (:S1 IS NOT NULL AND :S2 IS NULL AND (:S1=e.gewinner.name OR :S1=e.verlierer.name)) OR
        (:S1 IS NULL AND :S2 IS NOT NULL AND (:S2=e.gewinner.name OR :S2=e.verlierer.name)) OR
        (:S1 IS NULL AND :S2 IS NULL)
    """)
    List<Ergebnis1vs1> getFiltered(@Param("S1") String s1, @Param("S2") String s2);
}

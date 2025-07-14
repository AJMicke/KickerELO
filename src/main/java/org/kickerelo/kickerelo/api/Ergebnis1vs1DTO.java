package org.kickerelo.kickerelo.api;

import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.data.Spieler;

import java.time.LocalDate;

public record Ergebnis1vs1DTO(
        long id,
        Spieler gewinner,
        Spieler verlierer,
        short toreVerlierer,
        LocalDate datum
) {
    public Ergebnis1vs1DTO(Ergebnis1vs1 e) {
        this(
                e.getId(),
                e.getGewinner(),
                e.getVerlierer(),
                e.getToreVerlierer(),
                e.getTimestamp().toLocalDate()
        );
    }
}

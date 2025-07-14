package org.kickerelo.kickerelo.api;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;

import java.time.LocalDate;

public record Ergebnis2vs2DTO(
        long id,
        Spieler gewinnerVorn,
        Spieler gewinnerHinten,
        Spieler verliererVorn,
        Spieler verliererHinten,
        short toreVerlierer,
        LocalDate datum
) {
    public Ergebnis2vs2DTO(Ergebnis2vs2 e) {
        this(
                e.getId(),
                e.getGewinnerVorn(),
                e.getGewinnerHinten(),
                e.getVerliererVorn(),
                e.getVerliererHinten(),
                e.getToreVerlierer(),
                e.getTimestamp().toLocalDate()
        );
    }
}
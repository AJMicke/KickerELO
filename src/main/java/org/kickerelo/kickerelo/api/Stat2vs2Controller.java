package org.kickerelo.kickerelo.api;

import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.service.Stat2vs2Service;
import org.kickerelo.kickerelo.util.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats/2vs2")
public class Stat2vs2Controller {
    private final Stat2vs2Service service;
    private final SpielerRepository spielerRepository;
    private final Ergebnis2vs2Repository ergebnis2vs2Repository;

    public Stat2vs2Controller(Stat2vs2Service service, SpielerRepository spielerRepository, Ergebnis2vs2Repository ergebnis2vs2Repository) {
        this.service = service;
        this.spielerRepository = spielerRepository;
        this.ergebnis2vs2Repository = ergebnis2vs2Repository;
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Stat2vs2DTO> get2vs2StatsForPlayer(@PathVariable long playerId) {
        Spieler s = spielerRepository.findById(playerId).orElse(null);
        if (s == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(new Stat2vs2DTO(
                s.getElo2vs2(),
                ergebnis2vs2Repository.countByGewinnerVornOrGewinnerHintenOrVerliererVornOrVerliererHinten(s, s, s, s),
                service.getWinrate(s, Position.BOTH),
                service.getFrontRate(s),
                service.getWinrate(s, Position.FRONT),
                service.getWinrate(s, Position.BACK),
                ergebnis2vs2Repository.avgGoalDiffBack(s),
                ergebnis2vs2Repository.avgGoalDiffFront(s),
                service.getStreak(s)
        ));
    }
}

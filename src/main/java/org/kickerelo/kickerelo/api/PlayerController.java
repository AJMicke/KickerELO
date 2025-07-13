package org.kickerelo.kickerelo.api;

import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private  final SpielerRepository spielerRepository;

    public PlayerController(SpielerRepository spielerRepository) {
        this.spielerRepository = spielerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Spieler>> getAllPlayers() {
        return ResponseEntity.ok(spielerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spieler> getPlayer(@PathVariable Long id) {
        return spielerRepository.findById(id).map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}

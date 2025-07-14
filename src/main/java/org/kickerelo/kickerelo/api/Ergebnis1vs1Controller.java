package org.kickerelo.kickerelo.api;

import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/results/1vs1")
public class Ergebnis1vs1Controller {
    private final Ergebnis1vs1Repository ergebnis1vs1Repository;

    public Ergebnis1vs1Controller(Ergebnis1vs1Repository ergebnis1vs1Repository) {
        this.ergebnis1vs1Repository = ergebnis1vs1Repository;
    }

    @GetMapping
    public ResponseEntity<List<Ergebnis1vs1DTO>> getAllResults() {
        return ResponseEntity.ok(ergebnis1vs1Repository.findAll().stream().map(Ergebnis1vs1DTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ergebnis1vs1DTO> getResultForId(@PathVariable Long id) {
        return ergebnis1vs1Repository.findById(id).map(Ergebnis1vs1DTO::new).map(ResponseEntity::ok)
                                     .orElseGet(() -> ResponseEntity.noContent().build());
    }
}

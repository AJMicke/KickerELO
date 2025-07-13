package org.kickerelo.kickerelo.api;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/results/2vs2")
public class Ergebnis2vs2Controller {
    private final Ergebnis2vs2Repository ergebnis2vs2Repository;

    public Ergebnis2vs2Controller(Ergebnis2vs2Repository ergebnis2vs2Repository) {
        this.ergebnis2vs2Repository = ergebnis2vs2Repository;
    }

    @GetMapping
    public ResponseEntity<List<Ergebnis2vs2>> getAllResults() {
        return ResponseEntity.ok(ergebnis2vs2Repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ergebnis2vs2> getResultForId(@PathVariable Long id) {
        return ergebnis2vs2Repository.findById(id).map(ResponseEntity::ok)
                                     .orElseGet(() -> ResponseEntity.noContent().build());
    }
}

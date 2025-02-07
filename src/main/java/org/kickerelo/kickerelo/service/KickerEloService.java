package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.NoSuchPlayerException;
import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.Ergebnis1vs1TimeComparator;
import org.kickerelo.kickerelo.util.Ergebnis2vs2TimeComparator;
import org.kickerelo.kickerelo.util.Spieler1vs1EloComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class KickerEloService {
    @Autowired
    private Ergebnis1vs1Repository ergebnis1vs1Repository;
    @Autowired
    private Ergebnis2vs2Repository ergebnis2vs2Repository;
    @Autowired
    private SpielerRepository spielerRepository;
    @Autowired
    private EloCalculationService eloCalculationService;

    public List<String> getSpielerNamen() {
        return spielerRepository.findAll().stream().map(Spieler::getName).sorted().toList();
    }

    public List<Spieler> getSpielerEntities() {
        return spielerRepository.findAll().stream().sorted(new Spieler1vs1EloComparator()).toList();
    }

    public void enterResult1vs1(String gewinnerName, String verliererName,
                                short toreVerlierer) {
        if (gewinnerName == null || verliererName == null) {
            throw new PlayerNameNotSetException("Alle Namen müssen gesetzt sein");
        }

        if (gewinnerName.equals(verliererName)) {
            throw new DuplicatePlayerException("winner and loser identical");
        }
        if (toreVerlierer > 9 || toreVerlierer < 0) {
            throw new InvalidDataException("too many goals");
        }

        Spieler gewinner = spielerRepository.findByName(gewinnerName)
                .orElseThrow(() -> new NoSuchPlayerException(gewinnerName));

        Spieler verlierer = spielerRepository.findByName(verliererName)
                .orElseThrow(() -> new NoSuchPlayerException(verliererName));


        Ergebnis1vs1 ergebnis = new Ergebnis1vs1(gewinner, verlierer, toreVerlierer);

        ergebnis1vs1Repository.save(ergebnis);

        eloCalculationService.updateElo1vs1(gewinner, verlierer, toreVerlierer);
        spielerRepository.save(gewinner);
        spielerRepository.save(verlierer);

    }

    public void enterResult2vs2(String gewinnerNameVorn, String gewinnerNameHinten,
                                String verliererNameVorn, String verliererNameHinten,
                                short toreVerlierer) {

        if (gewinnerNameVorn == null || gewinnerNameHinten == null
            || verliererNameVorn == null || verliererNameHinten == null) {
            throw new PlayerNameNotSetException("Alle Namen müssen gesetzt sein");
        }

        if (gewinnerNameVorn.equals(gewinnerNameHinten) ||
            gewinnerNameVorn.equals(verliererNameVorn) ||
            gewinnerNameVorn.equals(verliererNameHinten) ||
            gewinnerNameHinten.equals(verliererNameVorn) ||
            gewinnerNameHinten.equals(verliererNameHinten) ||
            verliererNameVorn.equals(verliererNameHinten)) {
            throw new DuplicatePlayerException("players must not be identical");
        }

        if (toreVerlierer > 9 || toreVerlierer < 0) {
            throw new InvalidDataException("too many loser goals");
        }

        Spieler gewinnerVorn = spielerRepository.findByName(gewinnerNameVorn)
                .orElseThrow(() -> new NoSuchPlayerException(gewinnerNameVorn));

        Spieler gewinnerHinten = spielerRepository.findByName(gewinnerNameHinten)
                .orElseThrow(() -> new NoSuchPlayerException(gewinnerNameHinten));

        Spieler verliererVorn = spielerRepository.findByName(verliererNameVorn)
                .orElseThrow(() -> new NoSuchPlayerException(verliererNameVorn));

        Spieler verliererHinten = spielerRepository.findByName(verliererNameHinten)
                .orElseThrow(() -> new NoSuchPlayerException(verliererNameHinten));

        Ergebnis2vs2 ergebnis = new Ergebnis2vs2(gewinnerVorn, gewinnerHinten, verliererVorn, verliererHinten, toreVerlierer);

        ergebnis2vs2Repository.save(ergebnis);

        eloCalculationService.updateElo2vs2(gewinnerVorn, gewinnerHinten, verliererVorn, verliererHinten, toreVerlierer);
        spielerRepository.save(gewinnerVorn);
        spielerRepository.save(gewinnerHinten);
        spielerRepository.save(verliererVorn);
        spielerRepository.save(verliererHinten);
    }

    public void addSpieler(String name) {
        if (name == null || name.isBlank()) {
            throw new PlayerNameNotSetException("Leerer Name");
        }
        if (getSpielerNamen().contains(name)) {
            throw new DuplicatePlayerException("players must not be identical");
        }
        Spieler spieler = new Spieler();
        spieler.setName(name);
        spieler.setElo1vs1(eloCalculationService.getInitialElo1vs1());
        spieler.setElo2vs2(eloCalculationService.getInitialElo2vs2());
        spielerRepository.save(spieler);
    }

    public void recalculateAll1vs1() {
        HashMap<Integer, Spieler> players = new HashMap<>();
        for (Spieler spieler : spielerRepository.findAll()) {
            spieler.setElo1vs1(eloCalculationService.getInitialElo1vs1());
            players.put(spieler.getId(), spieler);
        }
        Stream<Ergebnis1vs1> results = ergebnis1vs1Repository.findAll().stream().sorted(new Ergebnis1vs1TimeComparator());
        results.forEach(r -> {
            eloCalculationService.updateElo1vs1(players.get(r.getGewinner().getId()),
                                                players.get(r.getVerlierer().getId()),
                                                r.getToreVerlierer());
        });
        spielerRepository.saveAll(players.values());
    }

    public void recalculateAll2vs2() {
        HashMap<Integer, Spieler> players = new HashMap<>();
        for (Spieler spieler : spielerRepository.findAll()) {
            spieler.setElo2vs2(eloCalculationService.getInitialElo2vs2());
            players.put(spieler.getId(), spieler);
        }
        Stream<Ergebnis2vs2> results = ergebnis2vs2Repository.findAll().stream().sorted(new Ergebnis2vs2TimeComparator());
        results.forEach(r -> {
            eloCalculationService.updateElo2vs2(players.get(r.getGewinnerVorn().getId()),
                                                players.get(r.getGewinnerHinten().getId()),
                                                players.get(r.getVerliererVorn().getId()),
                                                players.get(r.getVerliererHinten().getId()),
                                                r.getToreVerlierer());
        });
        spielerRepository.saveAll(players.values());
    }
}

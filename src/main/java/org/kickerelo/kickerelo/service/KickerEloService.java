package org.kickerelo.kickerelo.service;

import jakarta.transaction.Transactional;
import org.kickerelo.kickerelo.data.AuthentikUser;
import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.repository.AuthentikUserRepository;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.AccessControlService;
import org.kickerelo.kickerelo.util.EloChange1vs1;
import org.kickerelo.kickerelo.util.EloChange2vs2;
import org.kickerelo.kickerelo.util.comparator.Ergebnis1vs1TimeComparator;
import org.kickerelo.kickerelo.util.comparator.Ergebnis2vs2TimeComparator;
import org.kickerelo.kickerelo.util.comparator.SpielerNameComparator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Provides all functions for the application
 */
@Service
public class KickerEloService {
    private final Ergebnis1vs1Repository ergebnis1vs1Repository;
    private final Ergebnis2vs2Repository ergebnis2vs2Repository;
    private final SpielerRepository spielerRepository;
    private final EloCalculationService eloCalculationService;
    private final AccessControlService accessControlService;
    private final AuthentikUserRepository userRepository;

    public KickerEloService(Ergebnis1vs1Repository ergebnis1vs1Repository,
                            Ergebnis2vs2Repository ergebnis2vs2Repository,
                            SpielerRepository spielerRepository,
                            EloCalculationService eloCalculationService,
                            AccessControlService accessControlService,
                            AuthentikUserRepository userRepository) {
        this.ergebnis1vs1Repository = ergebnis1vs1Repository;
        this.ergebnis2vs2Repository = ergebnis2vs2Repository;
        this.spielerRepository = spielerRepository;
        this.eloCalculationService = eloCalculationService;
        this.accessControlService = accessControlService;
        this.userRepository = userRepository;
        recalculateAll1vs1();
        recalculateAll2vs2();
    }

    /**
     * @return List of all player names sorted alphabetically
     */
    public List<String> getSpielerNamen() {
        return spielerRepository.findAll().stream().map(Spieler::getName).sorted().toList();
    }

    /**
     * @return List of all player entities sorted by 1 vs 1 ELO
     */
    public List<Spieler> getSpielerEntities() {
        return spielerRepository.findAll().stream().sorted(new SpielerNameComparator()).toList();
    }

    /**
     * Enter a result of a 1 vs 1 game
     * @param gewinner The winning player
     * @param verlierer The losing player
     * @param toreVerlierer The number of goals of the loser
     */
    @Transactional
    public void enterResult1vs1(Spieler gewinner, Spieler verlierer,
                                short toreVerlierer) {
        // Check if the inputs are valid
        if (gewinner == null || verlierer == null) {
            throw new PlayerNameNotSetException("Alle Namen müssen gesetzt sein");
        }

        if (gewinner.equals(verlierer)) {
            throw new DuplicatePlayerException("winner and loser identical");
        }
        if (toreVerlierer > 9 || toreVerlierer < 0) {
            throw new InvalidDataException("too many goals");
        }

        AuthentikUser currentUser = accessControlService.getCurrentUser().orElseThrow(() -> new RuntimeException("User not authenticated"));
        Ergebnis1vs1 ergebnis = new Ergebnis1vs1(gewinner, verlierer, toreVerlierer, currentUser);
        currentUser.addSubmittedResult(ergebnis);
        ergebnis = ergebnis1vs1Repository.save(ergebnis);
        userRepository.save(currentUser);

        EloChange1vs1 change = eloCalculationService.updateElo1vs1(gewinner, verlierer, toreVerlierer);
        EloChangeTracker.put1vs1Result(ergebnis.getId(), change);
        spielerRepository.save(gewinner);
        spielerRepository.save(verlierer);
    }

    /**
     * Enter the result of a 2 vs 2 game
     * @param gewinnerVorn winning offensive player
     * @param gewinnerHinten winning defensive player
     * @param verliererVorn losing offensive player
     * @param verliererHinten losing defensive player
     * @param toreVerlierer Number of goals of the losing team
     */
    @Transactional
    public void enterResult2vs2(Spieler gewinnerVorn, Spieler gewinnerHinten,
                                Spieler verliererVorn, Spieler verliererHinten,
                                short toreVerlierer) {
        // Check if the inputs are valid
        if (gewinnerVorn == null || gewinnerHinten == null
            || verliererVorn == null || verliererHinten == null) {
            throw new PlayerNameNotSetException("Alle Namen müssen gesetzt sein");
        }

        if (gewinnerVorn.equals(gewinnerHinten) ||
            gewinnerVorn.equals(verliererVorn) ||
            gewinnerVorn.equals(verliererHinten) ||
            gewinnerHinten.equals(verliererVorn) ||
            gewinnerHinten.equals(verliererHinten) ||
            verliererVorn.equals(verliererHinten)) {
            throw new DuplicatePlayerException("players must not be identical");
        }

        if (toreVerlierer > 9 || toreVerlierer < 0) {
            throw new InvalidDataException("too many loser goals");
        }

        AuthentikUser currentUser = accessControlService.getCurrentUser().orElseThrow(() -> new RuntimeException("User not authenticated"));
        Ergebnis2vs2 ergebnis = new Ergebnis2vs2(gewinnerVorn, gewinnerHinten, verliererVorn, verliererHinten, toreVerlierer, currentUser);
        currentUser.addSubmittedResult(ergebnis);
        ergebnis = ergebnis2vs2Repository.save(ergebnis);
        userRepository.save(currentUser);

        EloChange2vs2 change = eloCalculationService.updateElo2vs2(gewinnerVorn, gewinnerHinten, verliererVorn, verliererHinten, toreVerlierer);
        EloChangeTracker.put2vs2Result(ergebnis.getId(), change);
        spielerRepository.save(gewinnerVorn);
        spielerRepository.save(gewinnerHinten);
        spielerRepository.save(verliererVorn);
        spielerRepository.save(verliererHinten);
    }

    /**
     * Add a new player to the system
     * @param name Name of the new player
     */
    public void addSpieler(String name) {
        // Check if the player name is valid
        if (name == null || name.isBlank()) {
            throw new PlayerNameNotSetException("Leerer Name");
        }
        if (getSpielerNamen().contains(name)) {
            throw new DuplicatePlayerException("players must not be identical");
        }
        if (name.length() > 30) {
            throw new InvalidDataException("Zu lang");
        }
        Spieler spieler = new Spieler();
        spieler.setName(name);
        spieler.setElo1vs1(eloCalculationService.getInitialElo1vs1());
        spieler.setElo2vs2(eloCalculationService.getInitialElo2vs2());
        spielerRepository.save(spieler);
    }

    /**
     * Recalculate and overwrite all 1 vs 1 ELOs.
     */
    public void recalculateAll1vs1() {
        HashMap<Integer, Spieler> players = new HashMap<>();
        for (Spieler spieler : spielerRepository.findAll()) {
            spieler.setElo1vs1(eloCalculationService.getInitialElo1vs1());
            players.put(spieler.getId(), spieler);
        }
        Stream<Ergebnis1vs1> results = ergebnis1vs1Repository.findAll().stream().sorted(new Ergebnis1vs1TimeComparator());
        results.forEach(r -> {
            EloChange1vs1 c = eloCalculationService.updateElo1vs1(players.get(r.getGewinner().getId()),
                                                                  players.get(r.getVerlierer().getId()),
                                                                  r.getToreVerlierer());
            EloChangeTracker.put1vs1Result(r.getId(), c);
        });
        spielerRepository.saveAll(players.values());
    }

    /**
     * Recalculate and overwrite all 2 vs 2 ELOs.
     */
    public void recalculateAll2vs2() {
        HashMap<Integer, Spieler> players = new HashMap<>();
        for (Spieler spieler : spielerRepository.findAll()) {
            spieler.setElo2vs2(eloCalculationService.getInitialElo2vs2());
            players.put(spieler.getId(), spieler);
        }
        Stream<Ergebnis2vs2> results = ergebnis2vs2Repository.findAll().stream().sorted(new Ergebnis2vs2TimeComparator());
        results.forEach(r -> {
            EloChange2vs2 c = eloCalculationService.updateElo2vs2(players.get(r.getGewinnerVorn().getId()),
                                                                  players.get(r.getGewinnerHinten().getId()),
                                                                  players.get(r.getVerliererVorn().getId()),
                                                                  players.get(r.getVerliererHinten().getId()),
                                                                  r.getToreVerlierer());
            EloChangeTracker.put2vs2Result(r.getId(), c);
        });
        spielerRepository.saveAll(players.values());
    }
}

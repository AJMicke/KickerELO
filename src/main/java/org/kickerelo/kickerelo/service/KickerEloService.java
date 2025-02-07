package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.NoSuchPlayerException;
import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.model.ResultInfo1vs1;
import org.kickerelo.kickerelo.model.ResultInfo2vs2;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.SpielerEloComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return spielerRepository.findAll().stream().sorted(new SpielerEloComparator()).toList();
    }

    public void enterResult1vs1(String gewinnerName, String verliererName,
                                short toreVerlierer) {
        if (gewinnerName == null || verliererName == null) {
            throw new PlayerNameNotSetException("Alle Namen müssen gesetzt sein");
        }

        if (gewinnerName.equals(verliererName)) {
            throw new DuplicatePlayerException("winner and loser identical");
        }

        Spieler gewinner = spielerRepository.findByName(gewinnerName)
                .orElseThrow(() -> new NoSuchPlayerException(gewinnerName));

        Spieler verlierer = spielerRepository.findByName(verliererName)
                .orElseThrow(() -> new NoSuchPlayerException(verliererName));


        Ergebnis1vs1 ergebnis = new Ergebnis1vs1(gewinner, verlierer, toreVerlierer);

        ergebnis1vs1Repository.save(ergebnis);

        ResultInfo1vs1 result = new ResultInfo1vs1(gewinner.getElo1vs1(), ergebnis.getToreVerlierer(), verlierer.getElo1vs1());
        eloCalculationService.updateElo1vs1(result);
        gewinner.setElo1vs1(result.getNewEloWinner());
        spielerRepository.save(gewinner);
        verlierer.setElo1vs1(result.getNewEloLoser());
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

        ResultInfo2vs2 result = new ResultInfo2vs2(gewinnerVorn.getElo2vs2(), gewinnerHinten.getElo2vs2(), verliererVorn.getElo2vs2(), verliererHinten.getElo2vs2(), toreVerlierer);
        eloCalculationService.updateElo2vs2(result);
        gewinnerVorn.setElo2vs2(result.getNewEloWinnerFront());
        spielerRepository.save(gewinnerVorn);
        gewinnerHinten.setElo2vs2(result.getNewEloWinnerBack());
        spielerRepository.save(gewinnerHinten);
        verliererVorn.setElo2vs2(result.getNewEloLoserFront());
        spielerRepository.save(verliererVorn);
        verliererHinten.setElo2vs2(result.getNewEloLoserBack());
        spielerRepository.save(verliererHinten);
    }

    public void addSpieler(String name) {
        Spieler spieler = new Spieler();
        spieler.setName(name);
        spieler.setElo1vs1(1500);
        spielerRepository.save(spieler);
    }
}

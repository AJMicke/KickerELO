package org.kickerelo.kickerelo;

import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
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

    public List<String> getSpielerNamen() {
        return spielerRepository.findAll().stream().map(Spieler::getName).toList();
    }

    public void enterResult1vs1(String gewinnerName, String verliererName,
                                short toreVerlierer) {

        Spieler gewinner = spielerRepository.findByName(gewinnerName)
                .orElseThrow(() -> new NoSuchPlayerException(gewinnerName));

        Spieler verlierer = spielerRepository.findByName(verliererName)
                .orElseThrow(() -> new NoSuchPlayerException(verliererName));


        Ergebnis1vs1 ergebnis = new Ergebnis1vs1(gewinner, verlierer, toreVerlierer);

        ergebnis1vs1Repository.save(ergebnis);

        // Compute the new ELO and update the Spieler entities
    }

    public void enterResult2vs2(String gewinnerNameVorn, String gewinnerNameHinten,
                                String verliererNameVorn, String verliererNameHinten,
                                short toreVerlierer) {

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

        // Compute the new ELO, update the Spieler entitities
    }

    public void addSpieler(String name) {
        Spieler spieler = new Spieler();
        spieler.setName(name);
        spieler.setElo(1500);
        spielerRepository.save(spieler);
    }
}

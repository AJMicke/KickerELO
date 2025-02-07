package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.data.Spieler;
import org.springframework.stereotype.Service;



@Service
public class EloCalculationService {
    final float initialElo1vs1 = 1500;
    final float initialElo2vs2 = 1500;

    public void updateElo1vs1(Spieler gewinner, Spieler verlierer, short toreVerlierer) {
        gewinner.setElo1vs1(gewinner.getElo1vs1() + 10 - toreVerlierer);
        verlierer.setElo1vs1(verlierer.getElo1vs1() - 10 + toreVerlierer);
    }

    public void updateElo2vs2(Spieler gewinnerVorn, Spieler gewinnerHinten, Spieler verliererVorn, Spieler verliererHinten, short toreVerlierer) {
        gewinnerVorn.setElo2vs2(gewinnerVorn.getElo2vs2() + 10 - toreVerlierer);
        gewinnerHinten.setElo2vs2(gewinnerHinten.getElo2vs2() + 10 - toreVerlierer);
        verliererVorn.setElo2vs2(verliererVorn.getElo2vs2() - 10 + toreVerlierer);
        verliererHinten.setElo2vs2(verliererHinten.getElo2vs2());
    }

    public float getInitialElo1vs1() {
        return initialElo1vs1;
    }

    public float getInitialElo2vs2() {
        return initialElo2vs2;
    }
}

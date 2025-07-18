package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.util.EloChange1vs1;
import org.kickerelo.kickerelo.util.EloChange2vs2;
import org.springframework.stereotype.Service;


/**
 * This contains the math for calculating ELO only.
 */
@Service
public class EloCalculationService {
    private final float initialElo1vs1 = 1500;
    private final float initialElo2vs2 = 1500;

    /**
     * Updates the 1 vs 1 ELOs of the players according to the result of the game.
     * @param gewinner The entity representing the winning player
     * @param verlierer The entity representing the losing player
     * @param toreVerlierer The number of goals of the losing player
     */
    public EloChange1vs1 updateElo1vs1(Spieler gewinner, Spieler verlierer, short toreVerlierer) {
        final float baseK = 50;
        final float reductionPerGoal = 0.1f * baseK;
        final float finalK = baseK - (reductionPerGoal * toreVerlierer);

        double x = Math.pow(10, (verlierer.getElo1vs1() - gewinner.getElo1vs1()) / 400);
        float eloChange = (float) (finalK * x / (x + 1));

        gewinner.setElo1vs1(gewinner.getElo1vs1() + eloChange);
        verlierer.setElo1vs1(verlierer.getElo1vs1() - eloChange);

        return new EloChange1vs1(eloChange, -eloChange);
    }

    /**
     * Updates the 2 vs 2 ELOs of the players according to the result of the game
     * @param gewinnerVorn The winning offensive player
     * @param gewinnerHinten The winning defensive player
     * @param verliererVorn The losing offensive player
     * @param verliererHinten The losing defensive player
     * @param toreVerlierer The number of goals of the losing teams
     */
    public EloChange2vs2 updateElo2vs2(Spieler gewinnerVorn, Spieler gewinnerHinten, Spieler verliererVorn, Spieler verliererHinten, short toreVerlierer) {
        final float baseK = 100;
        final double adjustedK = baseK * (1 - (0.1 * toreVerlierer));
        var totalWinnerElo  = gewinnerVorn.getElo2vs2() + gewinnerHinten.getElo2vs2();
        var totalLoserElo = verliererVorn.getElo2vs2() + verliererHinten.getElo2vs2();
        var eloDifference = totalWinnerElo - totalLoserElo;
        var winnerProbability = 1 / (1 + Math.pow(10, -eloDifference / 500));

        var winner1EloChange = adjustedK * 0.5 * (1 - winnerProbability);
        var winner2EloChange = adjustedK * 0.5 * (1 - winnerProbability);
        var loser1EloChange = adjustedK * 0.5 * (0 - (1 - winnerProbability));
        var loser2EloChange = adjustedK * 0.5 * (0 - (1 - winnerProbability));

        gewinnerVorn.setElo2vs2((float) (gewinnerVorn.getElo2vs2() + winner1EloChange));
        gewinnerHinten.setElo2vs2((float) (gewinnerHinten.getElo2vs2() + winner2EloChange));
        verliererVorn.setElo2vs2((float) (verliererVorn.getElo2vs2() + loser1EloChange));
        verliererHinten.setElo2vs2((float) (verliererHinten.getElo2vs2() + loser2EloChange));

        return new EloChange2vs2(winner1EloChange, winner2EloChange, loser1EloChange, loser2EloChange);
    }

    public float getInitialElo1vs1() {
        return initialElo1vs1;
    }

    public float getInitialElo2vs2() {
        return initialElo2vs2;
    }
}

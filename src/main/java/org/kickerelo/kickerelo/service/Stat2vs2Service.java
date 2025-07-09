package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Stat2vs2Service {
    Ergebnis2vs2Repository ergebnis2vs2Repository;
    SpielerRepository spielerRepository;

    public Stat2vs2Service(Ergebnis2vs2Repository ergebnis2vs2Repository, SpielerRepository spielerRepository) {
        this.ergebnis2vs2Repository = ergebnis2vs2Repository;
        this.spielerRepository = spielerRepository;
    }

    public Float getWinrate(Spieler s, Position p) {
        int wins, losses;
        switch (p) {
            case BACK:
                wins = ergebnis2vs2Repository.countByGewinnerHinten(s);
                losses = ergebnis2vs2Repository.countByVerliererHinten(s);
                break;
            case FRONT:
                wins = ergebnis2vs2Repository.countByGewinnerVorn(s);
                losses = ergebnis2vs2Repository.countByVerliererVorn(s);
                break;
            default:
                wins = 0;
                losses = 0;
        }
        return (float) wins / (wins+losses);
    }

    public Float getFrontRate(Spieler s) {
        int numFront = ergebnis2vs2Repository.countByGewinnerVornOrVerliererVorn(s, s);
        int numAll = ergebnis2vs2Repository.countByGewinnerVornOrGewinnerHintenOrVerliererVornOrVerliererHinten(s, s, s, s);
        return (float) numFront / numAll;
    }


    public int getStreak(Spieler s) {
        List<Ergebnis2vs2> results = ergebnis2vs2Repository.getResultsForSpieler(s);
        if (results.isEmpty()) return 0;

        boolean isWinStreak = isWinner(s, results.get(0));
        int number = (int) results.stream().takeWhile(x -> isWinner(s, x) == isWinStreak).count();

        return number * (isWinStreak ? 1 : -1);
    }

    protected boolean isWinner(Spieler s, Ergebnis2vs2 r) {
        return r.getGewinnerVorn().equals(s) || r.getGewinnerHinten().equals(s);
    }
}

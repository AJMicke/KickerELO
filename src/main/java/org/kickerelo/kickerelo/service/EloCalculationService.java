package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.model.ResultInfo1vs1;
import org.kickerelo.kickerelo.model.ResultInfo2vs2;
import org.springframework.stereotype.Service;

@Service
public class EloCalculationService {
    public void updateElo1vs1(ResultInfo1vs1 result) {
        result.setNewEloWinner(result.getOldEloWinner() + 1);
        result.setNewEloLoser(result.getOldEloLoser() - 1);
    }

    public void updateElo2vs2(ResultInfo2vs2 result) {
        result.setNewEloWinnerFront(result.getOldEloWinnerFront() + 1);
        result.setNewEloWinnerBack(result.getOldEloWinnerBack() + 1);
        result.setNewEloLoserFront(result.getOldEloLoserFront() - 1);
        result.setNewEloLoserBack(result.getOldEloLoserBack() - 1);
    }
}

package org.kickerelo.kickerelo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EloChangeService {
    public record UpdateElo1vs1Change(double winnerEloChange, double loserEloChange) {
    }
    public record UpdateElo2vs2Change(double winnerFrontEloChange, double winnerBackEloChange, double loserFrontEloChange, double loserBackEloChange) {
    }

    Map<Long, UpdateElo1vs1Change> changes1vs1 = new HashMap<>();
    Map<Long, UpdateElo2vs2Change> changes2vs2 = new HashMap<>();

    public void put1vs1Result(long gameId, double winnerEloChange, double loserEloChange) {
        changes1vs1.put(gameId, new UpdateElo1vs1Change(winnerEloChange, loserEloChange));
    }

    public void put2vs2Result(long gameId, double winnerFrontEloChange, double winnerBackEloChange, double loserFrontEloChange, double loserBackEloChange) {
        changes2vs2.put(gameId, new UpdateElo2vs2Change(winnerFrontEloChange, winnerBackEloChange, loserFrontEloChange, loserBackEloChange));
    }

    public UpdateElo1vs1Change get1vs1Result(long gameId) {
        var result = changes1vs1.get(gameId);
        assert result != null;
        return result;
    }

    public UpdateElo2vs2Change get2vs2Result(long gameId) {
        var result = changes2vs2.get(gameId);
        assert result != null;
        return result;
    }
}

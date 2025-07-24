package org.kickerelo.kickerelo.api;

public record Stat2vs2DTO(
        Float elo,
        int numGames,
        int numWins,
        int numLosses,
        int numWinsFront,
        int numWinsBack,
        int numLossesFront,
        int numLossesBack,
        Float winRate,
        Float frontPercentile,
        Float frontWinRate,
        Float backWinRate,
        Float goalDiffBack,
        Float goalDiffFront,
        int currentStreak
) {
}

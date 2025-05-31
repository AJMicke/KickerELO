package org.kickerelo.kickerelo.util;

public record EloChange2vs2(double winnerFrontEloChange,
                            double winnerBackEloChange,
                            double loserFrontEloChange,
                            double loserBackEloChange) {
}

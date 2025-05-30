package org.kickerelo.kickerelo.service;

import org.kickerelo.kickerelo.util.EloChange1vs1;
import org.kickerelo.kickerelo.util.EloChange2vs2;

import java.util.HashMap;
import java.util.Map;

public class EloChangeTracker {
    static Map<Long, EloChange1vs1> changes1vs1 = new HashMap<>();
    static Map<Long, EloChange2vs2> changes2vs2 = new HashMap<>();

    public static void put1vs1Result(Long gameId, EloChange1vs1 change) {
        changes1vs1.put(gameId, change);
    }

    public static void put2vs2Result(long gameId, EloChange2vs2 change) {
        changes2vs2.put(gameId, change);
    }

    public static EloChange1vs1 get1vs1Result(long gameId) {
        var result = changes1vs1.get(gameId);
        assert result != null;
        return result;
    }

    public static EloChange2vs2 get2vs2Result(long gameId) {
        var result = changes2vs2.get(gameId);
        assert result != null;
        return result;
    }
}

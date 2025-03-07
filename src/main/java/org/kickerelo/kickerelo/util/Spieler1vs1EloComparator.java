package org.kickerelo.kickerelo.util;

import org.kickerelo.kickerelo.data.Spieler;

import java.util.Comparator;

public class Spieler1vs1EloComparator implements Comparator<Spieler> {
    @Override
    public int compare(Spieler o1, Spieler o2) {
        return Float.compare(o2.getElo1vs1(), o1.getElo1vs1());
    }
}

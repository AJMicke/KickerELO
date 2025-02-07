package org.kickerelo.kickerelo.util;

import org.kickerelo.kickerelo.data.Spieler;

import java.util.Comparator;

public class SpielerEloComparator implements Comparator<Spieler> {
    @Override
    public int compare(Spieler o1, Spieler o2) {
        return Float.compare(o1.getElo(), o2.getElo());
    }
}

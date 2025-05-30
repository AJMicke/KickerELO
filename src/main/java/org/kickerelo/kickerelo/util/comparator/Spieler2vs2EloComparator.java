package org.kickerelo.kickerelo.util.comparator;

import org.kickerelo.kickerelo.data.Spieler;

import java.util.Comparator;

public class Spieler2vs2EloComparator implements Comparator<Spieler> {
    @Override
    public int compare(Spieler o1, Spieler o2) {
        return Float.compare(o2.getElo2vs2(), o1.getElo2vs2());
    }
}

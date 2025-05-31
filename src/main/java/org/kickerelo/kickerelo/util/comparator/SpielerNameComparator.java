package org.kickerelo.kickerelo.util.comparator;

import org.kickerelo.kickerelo.data.Spieler;

import java.util.Comparator;

public class SpielerNameComparator implements Comparator<Spieler> {
    @Override
    public int compare(Spieler o1, Spieler o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

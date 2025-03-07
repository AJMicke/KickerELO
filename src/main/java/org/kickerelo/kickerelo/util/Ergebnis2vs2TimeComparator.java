package org.kickerelo.kickerelo.util;

import org.kickerelo.kickerelo.data.Ergebnis2vs2;

import java.util.Comparator;

public class Ergebnis2vs2TimeComparator implements Comparator<Ergebnis2vs2> {
    @Override
    public int compare(Ergebnis2vs2 o1, Ergebnis2vs2 o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}

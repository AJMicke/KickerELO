package org.kickerelo.kickerelo.util;

import org.kickerelo.kickerelo.data.Ergebnis1vs1;

import java.util.Comparator;

public class Ergebnis1vs1TimeComparator implements Comparator<Ergebnis1vs1> {
    @Override
    public int compare(Ergebnis1vs1 o1, Ergebnis1vs1 o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}

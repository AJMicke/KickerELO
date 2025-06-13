package org.kickerelo.kickerelo.views;

import java.util.ArrayList;
import java.util.List;

import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.comparator.Spieler2vs2EloComparator;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("graph2vs2")
public class Graph2vs2View extends VerticalLayout {

    public Graph2vs2View(SpielerRepository repo) {
        setSizeFull();
        H2 subheading = new H2("2 vs 2 Elo");

        List<String> names = new ArrayList<>();
        List<Float> elo = new ArrayList<>();

        repo.getSpielerWith2vs2Games().stream().sorted(new Spieler2vs2EloComparator()).forEach((s) -> {names.add(s.getName()); elo.add(s.getElo2vs2());});

        Chart chart = new Chart(names, elo);

        add(subheading, chart);
    }
}

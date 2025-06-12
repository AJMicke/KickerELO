package org.kickerelo.kickerelo.views;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.comparator.Spieler1vs1EloComparator;

@Route("graph1vs1")
public class Graph1vs1View extends VerticalLayout {

    public Graph1vs1View(SpielerRepository repo) {
        setSizeFull();
        H2 subheading = new H2("1 vs 1 Elo");

        List<String> names = new ArrayList<>();
        List<Float> elo = new ArrayList<>();

        repo.getSpielerWith1vs1Games().stream().sorted(new Spieler1vs1EloComparator()).forEach((s) -> {names.add(s.getName()); elo.add(s.getElo1vs1());});

        Chart chart = new Chart(names, elo);

        add(subheading, chart);
    }
}

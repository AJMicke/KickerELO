package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.util.comparator.Spieler2vs2EloComparator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Route("graph2vs2")
public class Graph2vs2View extends VerticalLayout {
    private final Ergebnis2vs2Repository ergebnisRepository;
    private final SpielerRepository spielerRepository;
    private Chart chart;

    public Graph2vs2View(Ergebnis2vs2Repository ergebnisRepository, SpielerRepository spielerRepository) {
        this.ergebnisRepository = ergebnisRepository;
        this.spielerRepository = spielerRepository;
        setSizeFull();
        H2 subheading = new H2("2 vs 2 Elo");

        Checkbox checkbox = new Checkbox();
        checkbox.setValue(true);
        checkbox.addValueChangeListener(event -> {
            remove(chart);
            chart = createChart(event.getValue());
            add(chart);
        });
        checkbox.setLabel("Nur aktive Spieler*innen anzeigen");
        checkbox.setTooltipText("Nur Spieler*innen anzeigen, die in den letzten 2 Wochen gespielt haben");
        chart = createChart(true);

        add(subheading, checkbox, chart);
    }

    private Chart createChart(boolean onlyActive) {
        List<String> names = new ArrayList<>();
        List<Float> elo = new ArrayList<>();

        spielerRepository.getSpielerWith2vs2Games().stream()
                .filter((spieler) -> {
                    if (!onlyActive) {
                        return true;
                    }

                    var results = ergebnisRepository.getResultsForSpieler(spieler);
                    return results.stream().anyMatch((result) -> {
                        var twoWeeksAgo = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusWeeks(2);
                        return result.getTimestamp().isAfter(twoWeeksAgo);
                    });
                })
                .sorted(new Spieler2vs2EloComparator()).forEach((s) -> {
                    names.add(s.getName());
                    elo.add(s.getElo2vs2());
                });
        return new Chart(names, elo);
    }
}

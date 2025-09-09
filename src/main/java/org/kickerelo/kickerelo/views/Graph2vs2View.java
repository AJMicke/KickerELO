package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.SpielerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route("graph2vs2")
public class Graph2vs2View extends VerticalLayout {
    private final SpielerRepository spielerRepository;
    private Chart chart;

    public Graph2vs2View(SpielerRepository spielerRepository) {
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

        List<Spieler> spieler = onlyActive ?
                spielerRepository.getSpielerWith2vs2GamesSince(LocalDateTime.now().minusWeeks(4))
                : spielerRepository.getSpielerWith2vs2Games();

        spieler.forEach((s) -> {names.add(s.getName()); elo.add(s.getElo2vs2());});

        return new Chart(names, elo);
    }
}

package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.repository.SpielerRepository;

@Route("app/graph1vs1")
public class Graph1vs1View extends VerticalLayout {

    ApexCharts chart1vs1;
    public Graph1vs1View(SpielerRepository repo) {
        setSizeFull();
        H2 subheading = new H2("1 vs 1 Elo");
        chart1vs1 = new Chart1vs1(repo.getSpielerWith1vs1Games()).build();
        chart1vs1.setWidth(100, Unit.PERCENTAGE);
        chart1vs1.setHeight(100, Unit.PERCENTAGE);

        add(subheading, chart1vs1);
    }
}

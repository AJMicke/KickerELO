package org.kickerelo.kickerelo.views;

import org.kickerelo.kickerelo.repository.SpielerRepository;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("app/graph2vs2")
public class Graph2vs2View extends VerticalLayout {
    ApexCharts chart2vs2;
    public Graph2vs2View(SpielerRepository repo) {
        setSizeFull();
        H2 subheading = new H2("2 vs 2 Elo");
        chart2vs2 = new Chart2vs2(repo.getSpielerWith2vs2Games()).build();
        chart2vs2.setWidth(100, Unit.PERCENTAGE);
        chart2vs2.setHeight(100, Unit.PERCENTAGE);

        add(subheading, chart2vs2);
    }
}

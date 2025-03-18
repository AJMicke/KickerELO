package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.service.KickerEloService;

@Route("graph2vs2")
public class Graph2vs2View extends VerticalLayout {
    ApexCharts chart2vs2;
    public Graph2vs2View(KickerEloService service) {
        setSizeFull();
        H2 subheading = new H2("2 vs 2 Elo");
        chart2vs2 = new Chart2vs2(service.getSpielerEntities()).build();
        chart2vs2.setWidth(100, Unit.PERCENTAGE);
        chart2vs2.setHeight(100, Unit.PERCENTAGE);

        add(subheading, chart2vs2);
    }
}

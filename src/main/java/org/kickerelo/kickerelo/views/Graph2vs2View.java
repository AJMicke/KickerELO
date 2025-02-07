package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.service.KickerEloService;

@Route("graph2vs2")
public class Graph2vs2View extends VerticalLayout {
    ApexChartsBuilder chart2vs2;
    public Graph2vs2View(KickerEloService service) {
        H2 subheading = new H2("2 vs 2 Elo");
        chart2vs2 = new Chart2vs2(service.getSpielerEntities());
        add(chart2vs2.build(), subheading);
    }
}

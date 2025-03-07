package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("graph1vs1")
public class Graph1vs1View extends VerticalLayout {

    ApexChartsBuilder chart1vs1;
    public Graph1vs1View(KickerEloService service) {
        H2 subheading = new H2("1 vs 1 Elo");
        chart1vs1 = new Chart1vs1(service.getSpielerEntities());
        add(subheading, chart1vs1.build());
    }

}

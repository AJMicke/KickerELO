package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("graph1vs1")
public class Graph1vs1View extends VerticalLayout {
    ApexChartsBuilder chart1vs1 = new Chart1vs1();
    public Graph1vs1View() {
        add(chart1vs1.build());
    }

}

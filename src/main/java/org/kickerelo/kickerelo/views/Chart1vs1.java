package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Theme;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.chart.zoom.ZoomType;
import com.github.appreciated.apexcharts.config.theme.Mode;
import com.github.appreciated.apexcharts.config.theme.Monochrome;
import com.github.appreciated.apexcharts.config.xaxis.Labels;
import com.github.appreciated.apexcharts.config.xaxis.labels.Style;
import com.github.appreciated.apexcharts.config.yaxis.Title;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.theme.lumo.Lumo;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.util.Spieler1vs1EloComparator;

import java.math.BigDecimal;
import java.util.List;

public class Chart1vs1 extends ApexChartsBuilder {
    public Chart1vs1(List<Spieler> l) {
        Theme theme = new Theme();
        Monochrome monochrome = new Monochrome();
        monochrome.setEnabled(true);
        theme.setMode(Mode.DARK);
        theme.setMonochrome(monochrome);
        Labels labels = new Labels();
        labels.setRotate(270d);
        labels.setShow(true);
        labels.setRotateAlways(false);


        withChart(ChartBuilder.get().withType(Type.SCATTER)
                .withZoom(ZoomBuilder.get().withEnabled(true).withType(ZoomType.XY).build()).build())
                .withSeries(new Series<>("ELO",
                        l.stream().sorted(new Spieler1vs1EloComparator()).map(Spieler::getElo1vs1).toArray()
                        ))
                .withXaxis(XAxisBuilder.get().withCategories(l.stream().sorted(new Spieler1vs1EloComparator())
                        .map(Spieler::getName).toList()).withLabels(labels).build())
                .withYaxis(YAxisBuilder.get().build())
                .withTheme(theme);
    }
}

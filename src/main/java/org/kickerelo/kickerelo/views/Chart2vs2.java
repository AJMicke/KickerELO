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
import com.github.appreciated.apexcharts.helper.Series;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.util.Spieler2vs2EloComparator;

import java.util.List;

public class Chart2vs2 extends ApexChartsBuilder {
    public Chart2vs2(List<Spieler> l) {
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
                        l.stream().sorted(new Spieler2vs2EloComparator()).map(Spieler::getElo2vs2).toArray()
                ))
                .withXaxis(XAxisBuilder.get().withCategories(l.stream().sorted(new Spieler2vs2EloComparator()).map(Spieler::getName).toList()).withLabels(labels).build())
                .withYaxis(YAxisBuilder.get().build())
                .withTheme(theme);
    }
}

package org.kickerelo.kickerelo.views;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.chart.zoom.ZoomType;
import com.github.appreciated.apexcharts.helper.Series;

import java.math.BigDecimal;

public class Chart1vs1 extends ApexChartsBuilder {
    public Chart1vs1() {
        withChart(ChartBuilder.get().withType(Type.SCATTER)
                .withZoom(ZoomBuilder.get().withEnabled(true).withType(ZoomType.XY).build()).build())
                .withSeries(new Series<>("ELO",
                        new BigDecimal[]{new BigDecimal(1), new BigDecimal(3)},
                        new BigDecimal[]{new BigDecimal(2), new BigDecimal(4)}))
                .withXaxis(XAxisBuilder.get().build()).withYaxis(YAxisBuilder.get().build());
    }
}

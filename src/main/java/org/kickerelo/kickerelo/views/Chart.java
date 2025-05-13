package org.kickerelo.kickerelo.views;

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

import org.json.JSONArray;

@Tag("canvas")
public class Chart extends Component {

    private final Element script = new Element("script");

    public Chart(List<String> xvalues, List<Float> yvalues) {
        setId("chart");

        UI.getCurrent().getPage().addJavaScript("https://cdn.jsdelivr.net/npm/chart.js");

        JSONArray x = new JSONArray(xvalues);
        JSONArray y = new JSONArray(yvalues);

        String js = "";

        // Dark mode setting
        js += "if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {";
        js +=     "Chart.defaults.color = 'hsla(214, 96%, 96%, 0.9)';";
        js +=     "Chart.defaults.borderColor = 'hsla(214, 78%, 88%, 0.5)';";
        js += "}";

        // Font size
        js += "Chart.defaults.font.size = 16;";

        // Chart
        js += "new Chart(document.getElementById('chart'), {type: 'line', ";
        js += "options: {showLine: false, pointRadius: 7, plugins: { legend: { display: false}}, layout: { padding: 10}}, ";

        // Data
        js += "data: { labels: " + x + ", datasets:[{data: " + y + ", ";
        js += "borderColor: 'hsl(214, 90%, 48%)', backgroundColor: 'hsl(214, 90%, 77%)'}]}});";

        script.setText(js);

        getElement().appendChild(script);
    }

}

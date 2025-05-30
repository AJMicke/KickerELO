package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import org.kickerelo.kickerelo.data.Spieler;

public class HistoryCommonView {
    private HistoryCommonView() {}

    static Div formatEloChange(Spieler spieler, double change) {
        Div div = new Div();

        Span nameSpan = new Span(spieler.getName() + " ");
        String formattedChange = String.format("%+.2f", change);
        Span changeSpan = new Span(formattedChange);

        changeSpan.getStyle().set("color", "var(--lumo-secondary-text-color)");
        changeSpan.getStyle().set("font-style", "italic");

        div.add(nameSpan, changeSpan);

        return div;
    }
}

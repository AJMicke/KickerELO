package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.service.EloChangeTracker;
import org.kickerelo.kickerelo.util.EloChange1vs1;
import org.kickerelo.kickerelo.util.EloChange2vs2;

public class HistoryView extends VerticalLayout {

    enum PlayerType1vs1 {
        GEWINNER,
        VERLIERER
    }
    enum PlayerType2vs2 {
        GEWINNER_VORN,
        GEWINNER_HINTEN,
        VERLIERER_VORN,
        VERLIERER_HINTEN
    }

    static Div formatEloChange(Ergebnis1vs1 ergebnis, PlayerType1vs1 type) {
        EloChange1vs1 change = EloChangeTracker.get1vs1Result(ergebnis.getId());
        return switch (type) {
            case GEWINNER -> formatEloChange(ergebnis.getGewinner(), change.winnerEloChange());
            case VERLIERER -> formatEloChange(ergebnis.getVerlierer(), change.loserEloChange());
        };
    }

    static Div formatEloChange(Ergebnis2vs2 ergebnis, PlayerType2vs2 type) {
        EloChange2vs2 change = EloChangeTracker.get2vs2Result(ergebnis.getId());
        return switch (type) {
            case GEWINNER_VORN -> formatEloChange(ergebnis.getGewinnerVorn(), change.winnerFrontEloChange());
            case GEWINNER_HINTEN -> formatEloChange(ergebnis.getGewinnerHinten(), change.winnerBackEloChange());
            case VERLIERER_VORN -> formatEloChange(ergebnis.getVerliererVorn(), change.loserFrontEloChange());
            case VERLIERER_HINTEN -> formatEloChange(ergebnis.getVerliererHinten(), change.loserBackEloChange());
        };
    }

    static Div formatEloChange(Spieler spieler, double change) {
        Div div = new Div();

        Span nameSpan = new Span(spieler.getName() + " ");
        String formattedChange = String.format("%+.2f", change);
        Span changeSpan = new Span(formattedChange);
        changeSpan.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY, LumoUtility.FontWeight.LIGHT);

        div.add(nameSpan, changeSpan);

        return div;
    }
}

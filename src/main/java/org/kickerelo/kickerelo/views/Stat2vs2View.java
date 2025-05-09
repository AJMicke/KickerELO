package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.kickerelo.kickerelo.service.Stat2vs2Service;
import org.kickerelo.kickerelo.util.Position;

@Route("stat2vs2")
public class Stat2vs2View extends VerticalLayout {
    Stat2vs2Service stat2vs2Service;
    KickerEloService kickerEloService;
    H2 subheading;
    ComboBox<Spieler> selector;
    Paragraph winrateBack;
    Paragraph winrateFront;
    public Stat2vs2View(Stat2vs2Service service, KickerEloService kickerService) {
        this.stat2vs2Service = service;
        this.kickerEloService = kickerService;
        subheading = new H2("2 vs 2 Ergebnis");
        selector = new ComboBox<>("Spieler");
        selector.setItems(kickerService.getSpielerEntities());
        selector.addValueChangeListener(event -> {
            updateData(selector.getValue());
        });
        winrateBack = new Paragraph("");
        winrateFront = new Paragraph("");

        add(subheading, selector, winrateBack, winrateFront);
    }

    private void updateData(Spieler s) {
        Float rateBack = stat2vs2Service.getWinrate(s, Position.BACK);
        Float rateFront = stat2vs2Service.getWinrate(s, Position.FRONT);
        winrateBack.setText("Winrate hinten: " + (rateBack.isNaN() ? "-" : rateBack));
        winrateFront.setText("Winrate vorne: " + (rateFront.isNaN() ? "-" : rateFront));
    }
}

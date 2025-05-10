package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
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
    ProgressBar frontRate = new ProgressBar();
    NativeLabel frontRateText = new NativeLabel();
    ProgressBar winRateFront = new ProgressBar();
    NativeLabel winRateFrontText = new NativeLabel();
    ProgressBar winRateBack = new ProgressBar();
    NativeLabel winRateBackText = new NativeLabel();

    public Stat2vs2View(Stat2vs2Service service, KickerEloService kickerService) {
        this.stat2vs2Service = service;
        this.kickerEloService = kickerService;
        subheading = new H2("2 vs 2 Ergebnis");
        selector = new ComboBox<>("Spieler");
        selector.setItems(kickerService.getSpielerEntities());
        selector.addValueChangeListener(event -> updateData(selector.getValue()));
        add(subheading, selector, frontRateText, frontRate, winRateFrontText, winRateFront, winRateBackText, winRateBack);
    }

    private void updateData(Spieler s) {
        updateFrontRate(s);
        updateFrontWinrate(s);
        updateBackWinrate(s);
    }

    private void updateFrontRate(Spieler s) {
        String text = "Anteil Spiele vorne: ";
        Float rateFrontGames = stat2vs2Service.getFrontRate(s);
        frontRate.setValue(rateFrontGames);
        frontRateText.setText(rateFrontGames.isNaN() ? text + "-" : text + String.format("%.2f", rateFrontGames * 100) + "%");
    }

    private void updateFrontWinrate(Spieler s) {
        String text = "Winrate vorne: ";
        Float winRate = stat2vs2Service.getWinrate(s, Position.FRONT);
        winRateFront.setValue(winRate);
        winRateBack.removeThemeVariants(ProgressBarVariant.LUMO_SUCCESS, ProgressBarVariant.LUMO_ERROR);
        winRateFront.addThemeVariants((winRate > 0.5f ? ProgressBarVariant.LUMO_SUCCESS : ProgressBarVariant.LUMO_ERROR));
        winRateFrontText.setText(winRate.isNaN() ? text + "-" : text + String.format("%.2f", winRate * 100) + "%");
    }

    private void updateBackWinrate(Spieler s) {
        String text = "Winrate hinten: ";
        Float winRate = stat2vs2Service.getWinrate(s, Position.BACK);
        winRateBack.setValue(winRate);
        winRateBack.removeThemeVariants(ProgressBarVariant.LUMO_SUCCESS, ProgressBarVariant.LUMO_ERROR);
        winRateBack.addThemeVariants((winRate > 0.5f ? ProgressBarVariant.LUMO_SUCCESS : ProgressBarVariant.LUMO_ERROR));
        winRateBackText.setText(winRate.isNaN() ? text + "-" : text + String.format("%.2f", winRate * 100) + "%");
    }
}

package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.kickerelo.kickerelo.service.Stat2vs2Service;
import org.kickerelo.kickerelo.util.Position;

@Route("stat2vs2")
public class Stat2vs2View extends VerticalLayout {
    Stat2vs2Service stat2vs2Service;
    KickerEloService kickerEloService;
    Ergebnis2vs2Repository repo;
    H2 subheading;
    ComboBox<Spieler> selector;
    Paragraph generalInfo = new Paragraph();
    ProgressBar frontRate = new ProgressBar();
    NativeLabel frontRateText = new NativeLabel();
    ProgressBar winRateFront = new ProgressBar();
    NativeLabel winRateFrontText = new NativeLabel();
    ProgressBar winRateBack = new ProgressBar();
    NativeLabel winRateBackText = new NativeLabel();
    Paragraph goalDiffBack = new Paragraph();
    Paragraph goalDiffFront = new Paragraph();

    public Stat2vs2View(Stat2vs2Service service, KickerEloService kickerService, Ergebnis2vs2Repository repo) {
        this.stat2vs2Service = service;
        this.kickerEloService = kickerService;
        this.repo = repo;
        subheading = new H2("2 vs 2 Spielerstatistik");
        selector = new ComboBox<>("Spieler");
        selector.setItems(kickerService.getSpielerEntities());
        selector.addValueChangeListener(event -> updateData(selector.getValue()));
        add(subheading, selector, generalInfo, frontRateText, frontRate, winRateFrontText, winRateFront, winRateBackText, winRateBack, goalDiffBack, goalDiffFront);
    }

    private void updateData(Spieler s) {
        updateGeneralInfo(s);
        updateFrontRate(s);
        updateFrontWinrate(s);
        updateBackWinrate(s);
        updateGoalDiffs(s);
    }

    private void updateGeneralInfo(Spieler s) {
        int anzahl = repo.countByGewinnerVornOrGewinnerHintenOrVerliererVornOrVerliererHinten(s, s, s, s);
        float elo = s.getElo2vs2();
        String text = String.format("%.2f", elo) + " Elo bei " + anzahl + " Spielen";
        generalInfo.setText(text);
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
        winRateFront.removeThemeVariants(ProgressBarVariant.LUMO_SUCCESS, ProgressBarVariant.LUMO_ERROR);
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

    private void updateGoalDiffs(Spieler s) {
        String text = "Mittlere Tordifferenz hinten: ";
        Float backDiff = repo.avgGoalDiffBack(s);
        goalDiffBack.setText(backDiff.isNaN() ? text + "-" : text + String.format("%.2f", backDiff));
        text = "Mittlere Tordifferenz vorne: ";
        Float frontDiff = repo.avgGoalDiffFront(s);
        goalDiffFront.setText(frontDiff.isNaN() ? text + "-" : text + String.format("%.2f", frontDiff));

    }
}

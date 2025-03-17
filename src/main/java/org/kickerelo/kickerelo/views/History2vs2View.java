package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Ergebnis2vs2;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;

import java.util.List;

@Route("history2vs2")
public class History2vs2View extends VerticalLayout {
    public History2vs2View(Ergebnis2vs2Repository repo) {
        setSizeFull();
        H2 subheading = new H2("Spiele 2 vs 2");
        List<Ergebnis2vs2> res = repo.findAll();
        Grid<Ergebnis2vs2> grid = new Grid<>(Ergebnis2vs2.class);

        grid.setItems(res);
        grid.removeColumnByKey("id");

        Grid.Column<Ergebnis2vs2> winnerFront = grid.getColumnByKey("gewinnerVorn");
        winnerFront.setHeader("Gewinner vorne");
        Grid.Column<Ergebnis2vs2> winnerBack = grid.getColumnByKey("gewinnerHinten");
        winnerBack.setHeader("Gewinner hinten");
        Grid.Column<Ergebnis2vs2> loserFront = grid.getColumnByKey("verliererVorn");
        loserFront.setHeader("Verlierer vorne");
        Grid.Column<Ergebnis2vs2> loserBack = grid.getColumnByKey("verliererHinten");
        loserBack.setHeader("Verlierer hinten");
        Grid.Column<Ergebnis2vs2> goals = grid.getColumnByKey("toreVerlierer");
        goals.setHeader("Verlierertore");
        Grid.Column<Ergebnis2vs2> timestamp = grid.getColumnByKey("timestamp");
        timestamp.setHeader("Zeitpunkt");
        timestamp.setRenderer(new LocalDateTimeRenderer<>(Ergebnis2vs2::getTimestamp, "dd.MM.yy HH:mm"));

        grid.setColumnOrder(winnerFront, winnerBack, loserFront, loserBack, goals, timestamp);
        GridSortOrder<Ergebnis2vs2> sortOrder = new GridSortOrder<>(timestamp, SortDirection.DESCENDING);
        grid.sort(List.of(sortOrder));
        add(subheading, grid);
    }
}

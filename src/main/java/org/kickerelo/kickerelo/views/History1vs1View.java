package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;

import java.util.List;

@Route("history1vs1")
public class History1vs1View extends VerticalLayout {
    public History1vs1View(Ergebnis1vs1Repository repo) {
        setSizeFull();
        H2 subheading = new H2("Spiele 1 vs 1");
        List<Ergebnis1vs1> res = repo.findAll();
        Grid<Ergebnis1vs1> grid = new Grid<>(Ergebnis1vs1.class);

        grid.setItems(res);
        grid.removeColumnByKey("id");

        Grid.Column<Ergebnis1vs1> winnerColumn = grid.getColumnByKey("gewinner");
        winnerColumn.setHeader("Gewinner");
        Grid.Column<Ergebnis1vs1> loserColumn = grid.getColumnByKey("verlierer");
        loserColumn.setHeader("Verlierer");
        Grid.Column<Ergebnis1vs1> goals = grid.getColumnByKey("toreVerlierer");
        goals.setHeader("Verlierertore");
        Grid.Column<Ergebnis1vs1> timestamp = grid.getColumnByKey("timestamp");
        timestamp.setHeader("Zeitpunkt");
        timestamp.setRenderer(new LocalDateTimeRenderer<>(Ergebnis1vs1::getTimestamp, "dd.MM.yy HH:mm"));

        grid.setColumnOrder(winnerColumn, loserColumn, goals, timestamp);
        GridSortOrder<Ergebnis1vs1> sortOrder = new GridSortOrder<>(timestamp, SortDirection.DESCENDING);
        grid.sort(List.of(sortOrder));
        add(subheading, grid);


    }
}

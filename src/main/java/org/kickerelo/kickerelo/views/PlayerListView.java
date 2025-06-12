package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.service.KickerEloService;

import java.util.List;

@Route("")
public class PlayerListView extends VerticalLayout {
    public PlayerListView(KickerEloService eloService) {
        setSizeFull();
        H2 subheading = new H2("Spielerliste");

        List<Spieler> players = eloService.getSpielerEntities();
        Grid<Spieler> playerGrid = new Grid<>(Spieler.class);

        playerGrid.setItems(players);
        playerGrid.removeColumnByKey("id");
        playerGrid.removeColumnByKey("elo_alt");
        Grid.Column<Spieler> nameColumn = playerGrid.getColumnByKey("name");
        Grid.Column<Spieler> elo1vs1Column = playerGrid.getColumnByKey("elo1vs1");
        Grid.Column<Spieler> elo2vs2Column = playerGrid.getColumnByKey("elo2vs2");
        nameColumn.setHeader("Name");
        elo1vs1Column.setHeader("Elo 1 vs 1");
        elo2vs2Column.setHeader("Elo 2 vs 2");

        playerGrid.setColumnOrder(nameColumn, elo1vs1Column, elo2vs2Column);

        GridSortOrder<Spieler> sortOrder = new GridSortOrder<>(elo1vs1Column, SortDirection.DESCENDING);
        playerGrid.sort(List.of(sortOrder));
        add(subheading, playerGrid);
    }
}

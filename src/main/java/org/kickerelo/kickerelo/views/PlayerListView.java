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

@Route("playerlist")
public class PlayerListView extends VerticalLayout {
    public PlayerListView(KickerEloService eloService) {
        H2 subheading = new H2("Spielerliste");

        List<Spieler> players = eloService.getSpielerEntities();
        Grid<Spieler> playerGrid = new Grid<>(Spieler.class);

        playerGrid.setItems(players);
        playerGrid.removeColumnByKey("id");
        playerGrid.removeColumnByKey("elo_alt");
        Grid.Column<Spieler> nameColumn = playerGrid.getColumnByKey("name");
        Grid.Column<Spieler> eloColumn = playerGrid.getColumnByKey("elo");
        nameColumn.setHeader("Name");
        eloColumn.setHeader("Elo");

        playerGrid.setColumnOrder(nameColumn, eloColumn);

        GridSortOrder<Spieler> sortOrder = new GridSortOrder<>(eloColumn, SortDirection.DESCENDING);
        playerGrid.sort(List.of(sortOrder));
        add(subheading, playerGrid);
    }
}

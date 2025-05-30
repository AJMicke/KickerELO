package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Ergebnis1vs1;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;

import java.util.List;

@Route("history1vs1")
public class History1vs1View extends HistoryView {

    private final Ergebnis1vs1Repository repo;

    List<Ergebnis1vs1> res;
    public History1vs1View(Ergebnis1vs1Repository repo) {
        this.repo = repo;

        setSizeFull();
        H2 subheading = new H2("Spiele 1 vs 1");
        res = repo.findAll();

        Grid<Ergebnis1vs1> grid = new Grid<>(Ergebnis1vs1.class);
        GridListDataView<Ergebnis1vs1> dataView = grid.setItems(res);

        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidth("95%");
        TextField filter1 = new TextField();
        filter1.setPlaceholder("Spieler");
        filter1.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(event -> dataView.refreshAll());
        filter1.setMaxWidth("50%");
        TextField filter2 = new TextField();
        filter2.setPlaceholder("Spieler");
        filter2.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filter2.setValueChangeMode(ValueChangeMode.EAGER);
        filter2.addValueChangeListener(event -> dataView.refreshAll());
        filter2.setMaxWidth("50%");

        filterLayout.add(filter1, filter2);

        grid.removeColumnByKey("id");
        Grid.Column<Ergebnis1vs1> winnerColumn = grid.getColumnByKey("gewinner");
        winnerColumn.setHeader("Gewinner");
        winnerColumn.setRenderer(new ComponentRenderer<>(ergebnis -> formatEloChange(ergebnis, PlayerType1vs1.GEWINNER)));
        Grid.Column<Ergebnis1vs1> loserColumn = grid.getColumnByKey("verlierer");
        loserColumn.setHeader("Verlierer");
        loserColumn.setRenderer(new ComponentRenderer<>(ergebnis -> formatEloChange(ergebnis, PlayerType1vs1.VERLIERER)));
        Grid.Column<Ergebnis1vs1> goals = grid.getColumnByKey("toreVerlierer");
        goals.setHeader("Verlierertore");
        Grid.Column<Ergebnis1vs1> timestamp = grid.getColumnByKey("timestamp");
        timestamp.setHeader("Zeitpunkt");
        timestamp.setRenderer(new LocalDateTimeRenderer<>(Ergebnis1vs1::getTimestamp, "dd.MM.yy"));
        grid.setColumnOrder(winnerColumn, loserColumn, goals, timestamp);
        GridSortOrder<Ergebnis1vs1> sortOrder = new GridSortOrder<>(timestamp, SortDirection.DESCENDING);
        grid.sort(List.of(sortOrder));

        dataView.addFilter(result -> {
            String name1 = result.getGewinner().getName().toLowerCase();
            String name2 = result.getVerlierer().getName().toLowerCase();
            String s1 = filter1.getValue();
            if (s1 != null) s1 = s1.toLowerCase();
            boolean p1 = !(s1 == null || s1.isEmpty());
            String s2 = filter2.getValue();
            if (s2 != null) s2 = s2.toLowerCase();
            boolean p2 = !(s2 == null || s2.isEmpty());

            if (p1 && p2) {
                return name1.contains(s1) && name2.contains(s2) || name2.contains(s1) && name1.contains(s2);
            }
            if (p1) {
                return name1.contains(s1) || name2.contains(s1);
            }
            if (p2) {
                return name1.contains(s2) || name2.contains(s2);
            }
            return true;
        });


        add(subheading, filterLayout, grid);


    }
}

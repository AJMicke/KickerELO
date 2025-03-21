package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
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
        GridListDataView<Ergebnis2vs2> dataView = grid.setItems(res);

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

        dataView.addFilter(result -> {
            String name1 = result.getGewinnerVorn().getName();
            String name2 = result.getVerliererVorn().getName();
            String name3 = result.getVerliererHinten().getName();
            String name4 = result.getGewinnerHinten().getName();
            String s1 = filter1.getValue();
            boolean p1 = !(s1 == null || s1.isEmpty());
            String s2 = filter2.getValue();
            boolean p2 = !(s2 == null || s2.isEmpty());

            if (p1 && p2) {
                return name1.contains(s1) && name2.contains(s2) || name2.contains(s1) && name1.contains(s2)
                    || name1.contains(s1) && name3.contains(s2) || name3.contains(s1) && name1.contains(s2)
                    || name1.contains(s1) && name4.contains(s2) || name4.contains(s1) && name1.contains(s2)
                    || name2.contains(s1) && name3.contains(s2) || name3.contains(s1) && name2.contains(s2)
                    || name2.contains(s1) && name4.contains(s2) || name4.contains(s1) && name2.contains(s2)
                    || name3.contains(s1) && name4.contains(s2) || name4.contains(s1) && name3.contains(s2);
            }
            if (p1) {
                return name1.contains(s1) || name2.contains(s1) || name3.contains(s1) || name4.contains(s1);
            }
            if (p2) {
                return name1.contains(s2) || name2.contains(s2) || name3.contains(s2) || name4.contains(s2);
            }
            return true;
        });

        add(subheading, filterLayout, grid);
    }
}

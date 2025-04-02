package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
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

        HorizontalLayout team1Layout = new HorizontalLayout();
        team1Layout.setWidth("95%");
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
        team1Layout.add(new Paragraph("Team 1"), filter1, filter2);

        HorizontalLayout team2Layout = new HorizontalLayout();
        team1Layout.setWidth("95%");
        TextField filter3 = new TextField();
        filter3.setPlaceholder("Spieler");
        filter3.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filter3.setValueChangeMode(ValueChangeMode.EAGER);
        filter3.addValueChangeListener(event -> dataView.refreshAll());
        filter3.setMaxWidth("50%");
        TextField filter4 = new TextField();
        filter4.setPlaceholder("Spieler");
        filter4.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filter4.setValueChangeMode(ValueChangeMode.EAGER);
        filter4.addValueChangeListener(event -> dataView.refreshAll());
        filter4.setMaxWidth("50%");
        team2Layout.add(new Paragraph("Team 2"), filter3, filter4);

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
            String winF = result.getGewinnerVorn().getName().toLowerCase();
            String losF = result.getVerliererVorn().getName().toLowerCase();
            String losB = result.getVerliererHinten().getName().toLowerCase();
            String winB = result.getGewinnerHinten().getName().toLowerCase();
            String t11 = filter1.getValue() != null ? filter1.getValue().toLowerCase() : "";
            String t12 = filter2.getValue() != null ? filter2.getValue().toLowerCase() : "";
            String t21 = filter3.getValue() != null ? filter3.getValue().toLowerCase() : "";
            String t22 = filter4.getValue() != null ? filter4.getValue().toLowerCase() : "";
            return checkTeamFits(t11, t12, winF, winB) && checkTeamFits(t21, t22, losF, losB)
                    || checkTeamFits(t11, t12, losF, losB) && checkTeamFits(t21, t22, winF, winB);

        });

        add(subheading, team1Layout, team2Layout, grid);
    }

    private boolean checkTeamFits(String p1, String p2, String tp1, String tp2) {
        if (p1.isBlank() && p2.isBlank()) return true;
        if (p1.isBlank()) return tp1.contains(p2) || tp2.contains(p2);
        if (p2.isBlank()) return tp1.contains(p1) || tp2.contains(p1);
        return (tp1.contains(p1) && tp2.contains(p2)) || (tp2.contains(p1) && tp1.contains(p2));
    }
}

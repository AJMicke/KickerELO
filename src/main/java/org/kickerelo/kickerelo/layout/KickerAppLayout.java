package org.kickerelo.kickerelo.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import org.kickerelo.kickerelo.views.*;

@Layout
@JsModule("prefers-color-scheme.js")
public class KickerAppLayout extends AppLayout {

    public KickerAppLayout() {
        DrawerToggle drawerToggle = new DrawerToggle();

        H1 title = new H1("Kicker-ELO");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        addToNavbar(drawerToggle, title);

        RouterLink enter1vs1 = new RouterLink("1 vs 1", Enter1vs1View.class);
        RouterLink enter2vs2 = new RouterLink("2 vs 2", Enter2vs2View.class);
        RouterLink playerList = new RouterLink("Spielerliste", PlayerListView.class);
        RouterLink graph1vs1 = new RouterLink("Graph 1 vs 1", Graph1vs1View.class);
        RouterLink graph2vs2 = new RouterLink("Graph 2 vs 2", Graph2vs2View.class);
        RouterLink history1vs1 = new RouterLink("Resultate 1 vs 1", History1vs1View.class);
        RouterLink history2vs2 = new RouterLink("Resultate 2 vs 2", History2vs2View.class);
        RouterLink admin = new RouterLink("Verwaltung", AdminView.class);

        Tabs tabs = new Tabs(new Tab(playerList), new Tab(enter1vs1), new Tab(enter2vs2), new Tab(graph1vs1),
                new Tab(graph2vs2), new Tab(history1vs1), new Tab(history2vs2), new Tab(admin));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }
}

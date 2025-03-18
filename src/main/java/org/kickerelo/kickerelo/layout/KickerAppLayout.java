package org.kickerelo.kickerelo.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
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

        RouterLink enter1vs1 = new RouterLink("1 vs 1 Ergebnis", Enter1vs1View.class);
        RouterLink enter2vs2 = new RouterLink("2 vs 2 Ergebnis", Enter2vs2View.class);
        RouterLink playerList = new RouterLink("Spielerliste", PlayerListView.class);
        RouterLink graph1vs1 = new RouterLink("Graph 1 vs 1", Graph1vs1View.class);
        RouterLink graph2vs2 = new RouterLink("Graph 2 vs 2", Graph2vs2View.class);
        RouterLink history1vs1 = new RouterLink("Resultate 1 vs 1", History1vs1View.class);
        RouterLink history2vs2 = new RouterLink("Resultate 2 vs 2", History2vs2View.class);
        RouterLink admin = new RouterLink("Verwaltung", AdminView.class);

        Tabs tabs = new Tabs(new Tab(VaadinIcon.GROUP.create(), playerList), new Tab(VaadinIcon.COG.create(), admin),
                new Tab(VaadinIcon.EDIT.create(), enter1vs1), new Tab(VaadinIcon.BAR_CHART.create(), graph1vs1), new Tab(VaadinIcon.RECORDS.create(), history1vs1),
                new Tab(VaadinIcon.EDIT.create(), enter2vs2), new Tab(VaadinIcon.BAR_CHART.create(), graph2vs2), new Tab(VaadinIcon.RECORDS.create(), history2vs2));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        SideNav general = new SideNav("Allgemein");
        general.setCollapsible(true);
        general.addItem(new SideNavItem("Spielerliste", PlayerListView.class, VaadinIcon.GROUP.create()),
                new SideNavItem("Verwaltung", AdminView.class, VaadinIcon.COG.create()));
        SideNav nav1 = new SideNav("1 vs 1");
        nav1.setCollapsible(true);
        nav1.addItem(new SideNavItem("Ergebnis eintragen",  Enter1vs1View.class, VaadinIcon.EDIT.create()),
                new SideNavItem("ELO-Graph",  Graph1vs1View.class, VaadinIcon.BAR_CHART.create()),
                new SideNavItem("Historie", History1vs1View.class, VaadinIcon.RECORDS.create()));
        SideNav nav2 = new SideNav("2 vs 2");
        nav2.setCollapsible(true);
        nav2.addItem(new SideNavItem("Ergebnis eintragen", Enter2vs2View.class, VaadinIcon.EDIT.create()),
                new SideNavItem("ELO-Graph", Graph2vs2View.class, VaadinIcon.BAR_CHART.create()),
                new SideNavItem("Historie", History2vs2View.class, VaadinIcon.RECORDS.create()));

        addToDrawer(general, nav1, nav2);
    }
}

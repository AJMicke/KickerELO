package org.kickerelo.kickerelo.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.kickerelo.kickerelo.views.Enter1vs1View;
import org.kickerelo.kickerelo.views.Enter2vs2View;

@Layout
public class KickerAppLayout extends AppLayout {

    public KickerAppLayout() {
        DrawerToggle drawerToggle = new DrawerToggle();

        H1 title = new H1("Kicker-ELO");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        addToNavbar(drawerToggle, title);

        RouterLink enter1vs1 = new RouterLink("1 vs 1", Enter1vs1View.class);
        RouterLink enter2vs2 = new RouterLink("2 vs 2", Enter2vs2View.class);

        Tabs tabs = new Tabs(new Tab(enter1vs1), new Tab(enter2vs2));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }
}

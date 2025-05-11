package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.service.KickerEloService;

@Route("app/admin")
public class AdminView extends VerticalLayout {
    public AdminView(KickerEloService service) {
        H2 subheader = new H2("Verwaltung");

        TextField spielername = new TextField("Spielername");
        spielername.addClassName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button addPlayerButton = new Button("Spieler hinzufügen", e -> {
            try {
                service.addSpieler(spielername.getValue());
            } catch (PlayerNameNotSetException err) {
                Notification.show("Spielername darf nicht leer sein").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            } catch (DuplicatePlayerException err) {
                Notification.show("Spieler existiert bereits").addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            } catch (InvalidDataException err) {
                Notification.show("Name zu lang").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            Notification.show("Spieler gespeichert").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        Button recalc1vs1Button = new Button("1 vs 1 Elo neu berechnen", e -> {
            Notification.show("Recalculating Elo").addThemeVariants(NotificationVariant.LUMO_WARNING);
            service.recalculateAll1vs1();
            Notification.show("Recalculating finished").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        Button recalc2vs2Button = new Button("2 vs 2 Elo neu berechnen", e -> {
            Notification.show("Recalculating Elo").addThemeVariants(NotificationVariant.LUMO_WARNING);
            service.recalculateAll2vs2();
            Notification.show("Recalculating finished").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        add(spielername, spielername, addPlayerButton, recalc1vs1Button, recalc2vs2Button);
    }
}

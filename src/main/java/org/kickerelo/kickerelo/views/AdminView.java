package org.kickerelo.kickerelo.views;

import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.kickerelo.kickerelo.util.AccessControlService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("admin")
public class AdminView extends VerticalLayout {

    public AdminView(KickerEloService service, AccessControlService accessControlService) {
        // Deny access if user isn't part of the Kicker Admin group
        if (!accessControlService.userAllowedForRole("Kicker Admin")) {
            add(new Paragraph("Du bist nicht berechtigt, diese Seite zu sehen."));
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }

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

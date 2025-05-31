package org.kickerelo.kickerelo.views;

import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("app/admin")
public class AdminView extends VerticalLayout {
    public AdminView(KickerEloService service) {
        // Zeige den aktuell authentifizierten Benutzer
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof OidcUser oidcUser) {
            String username = oidcUser.getPreferredUsername();
            Object groupsObj = oidcUser.getClaims().getOrDefault("groups", List.of());
            List<String> listOfGroups;
            if (groupsObj instanceof List<?> groupsList) {
                listOfGroups = groupsList.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .toList();
            } else {
                listOfGroups = List.of();
            }
            add(new Paragraph("Angemeldet als: " + username));
            add(new Paragraph("Gruppen: " + listOfGroups.toString()));

            if (listOfGroups.contains("Kicker Admin")) {
                add(new Paragraph("Du bist Admin!"));
            } else {
                if (listOfGroups.contains("Kicker User")) {
                    add(new Paragraph("Du bist kein Admin, aber ein kickerelo User!"));
                } else {
                    add(new Paragraph("Du bist gar nichts!"));
                }
            }
        } else {
            add(new Paragraph("Niemand ist angemeldet"));
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

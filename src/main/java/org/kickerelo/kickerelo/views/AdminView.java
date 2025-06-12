package org.kickerelo.kickerelo.views;

import java.util.List;

import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;

@Route("admin")
public class AdminView extends VerticalLayout {

    private final org.springframework.core.env.Environment environment;

    // Methode zum Prüfen, ob das "test"-Profil aktiv ist
    private boolean isTestProfileActive() {
        for (String profile : environment.getActiveProfiles()) {
            System.out.println("Active profile: " + profile);
            if ("prod".equals(profile)) {
                return true;
            }
        }
        return false;
    }

    public void beforeEnter(BeforeEnterEvent event) {
        if (!isTestProfileActive()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !(auth.getPrincipal() instanceof OidcUser oidcUser)) {
                event.rerouteTo("");
                return;
            }

            var groups = oidcUser.getClaimAsStringList("groups");
            if (groups == null || !groups.contains("Kicker Admin")) {
                event.rerouteTo("");
            }
        }
    }

    public AdminView(KickerEloService service, org.springframework.core.env.Environment environment) {
        this.environment = environment;
        
        if (!isTestProfileActive()) {
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

                if (!listOfGroups.contains("Kicker Admin")) {
                    add(new Paragraph("Du bist nicht berechtigt, diese Seite zu sehen."));
                    getUI().ifPresent(ui -> ui.navigate(""));
                    return;
                } else {
                    add(new Paragraph("Willkommen im Admin-Bereich!"));
                }
            } else {
                add(new Paragraph("Niemand ist angemeldet"));
                add(new Paragraph("Du bist nicht berechtigt, diese Seite zu sehen."));
                getUI().ifPresent(ui -> ui.navigate(""));
                return;
            }
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

package org.kickerelo.kickerelo.views;

import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.NoSuchPlayerException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.kickerelo.kickerelo.util.AccessControlService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

@Route("enter2vs2")
public class Enter2vs2View extends VerticalLayout {
    public Enter2vs2View(KickerEloService eloService, AccessControlService accessControlService) {
        // Deny access if user isn't part of the Kicker User group
        if (!accessControlService.userAllowedForRole("Kicker User") && !accessControlService.userAllowedForRole("Kicker Admin")) {
            add(new Paragraph("Du bist nicht berechtigt, diese Seite zu sehen."));
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }

        H2 subheading = new H2("2 vs 2 Ergebnis");

        ComboBox<Spieler> winnerFrontSelect = new ComboBox<>("Gewinner vorne");
        winnerFrontSelect.setItems(eloService.getSpielerEntities());
        winnerFrontSelect.setPlaceholder("Spieler auswählen");

        ComboBox<Spieler> winnerBackSelect = new ComboBox<>("Gewinner hinten");
        winnerBackSelect.setItems(eloService.getSpielerEntities());
        winnerBackSelect.setPlaceholder("Spieler auswählen");

        ComboBox<Spieler> loserFrontSelect = new ComboBox<>("Verlierer vorne");
        loserFrontSelect.setItems(eloService.getSpielerEntities());
        loserFrontSelect.setPlaceholder("Spieler auswählen");

        ComboBox<Spieler> loserBackSelect = new ComboBox<>("Verlierer hinten");
        loserBackSelect.setItems(eloService.getSpielerEntities());
        loserBackSelect.setPlaceholder("Spieler auswählen");

        IntegerField loserGoals = new IntegerField("Tore des Verlierers");
        loserGoals.setMin(0);
        loserGoals.setMax(9);
        loserGoals.setValue(0);
        loserGoals.setStepButtonsVisible(true);

        Button saveButton = new Button("Speichern", e -> {
            try {
                eloService.enterResult2vs2(winnerFrontSelect.getValue(), winnerBackSelect.getValue(), loserFrontSelect.getValue(), loserBackSelect.getValue(), loserGoals.getValue().shortValue());
                Notification.show("Gespeichert").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                winnerFrontSelect.setValue(null);
                winnerBackSelect.setValue(null);
                loserFrontSelect.setValue(null);
                loserBackSelect.setValue(null);
                loserGoals.setValue(0);
            } catch (NoSuchPlayerException err) {
                Notification.show("Unbekannter Spieler").addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (DuplicatePlayerException err) {
                Notification.show("Alle Spieler müssen paarweise verschieden sein").addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (PlayerNameNotSetException err) {
                Notification.show("Alle Spieler müssen gesetzt sein").addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (InvalidDataException err) {
                Notification.show("Verliertore falsch").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            e.getSource().setEnabled(true);
        });
        saveButton.setDisableOnClick(true);


        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        addClassName("centered-content");

        add(subheading, winnerFrontSelect, winnerBackSelect, loserFrontSelect, loserBackSelect, loserGoals, saveButton);
    }
}

package org.kickerelo.kickerelo.views;

import org.kickerelo.kickerelo.exception.DuplicatePlayerException;
import org.kickerelo.kickerelo.exception.InvalidDataException;
import org.kickerelo.kickerelo.exception.NoSuchPlayerException;
import org.kickerelo.kickerelo.exception.PlayerNameNotSetException;
import org.kickerelo.kickerelo.service.KickerEloService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

@Route(value = "app/enter1vs1")
public class Enter1vs1View extends VerticalLayout {

    public Enter1vs1View(KickerEloService eloService) {
        H2 subheading = new H2("1 vs 1 Ergebnis");

        ComboBox<String> winnerSelect = new ComboBox<>("Gewinner");
        winnerSelect.setItems(eloService.getSpielerNamen());
        winnerSelect.setPlaceholder("Spieler auswählen");

        ComboBox<String> loserSelect = new ComboBox<>("Verlierer");
        loserSelect.setItems(eloService.getSpielerNamen());
        loserSelect.setPlaceholder("Spieler auswählen");

        IntegerField loserGoals = new IntegerField("Tore des Verlierers");
        loserGoals.setMin(0);
        loserGoals.setMax(9);
        loserGoals.setValue(0);
        loserGoals.setStepButtonsVisible(true);

        Button saveButton = new Button("Speichern", e -> {
            try {
                eloService.enterResult1vs1(winnerSelect.getValue(), loserSelect.getValue(), loserGoals.getValue().shortValue());
                Notification.show("Gespeichert").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                winnerSelect.setValue(null);
                loserSelect.setValue(null);
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

        add(subheading, winnerSelect, loserSelect, loserGoals, saveButton);
    }
}

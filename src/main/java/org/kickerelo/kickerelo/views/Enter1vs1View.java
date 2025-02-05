package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.NoSuchPlayerException;
import org.kickerelo.kickerelo.service.KickerEloService;

@Route(value = "enter1vs1")
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
            } catch (NoSuchPlayerException err) {
                Notification.show("Konnte nicht gespeichert werden").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });



        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        addClassName("centered-content");

        add(subheading, winnerSelect, loserSelect, loserGoals, saveButton);
    }
}

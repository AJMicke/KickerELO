package org.kickerelo.kickerelo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     */

    public MainView(KickerEloService eloService) {


        TextField spielername = new TextField("Spielername");
        spielername.addClassName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Spieler hinzufügen", e -> {
            eloService.addSpieler(spielername.getValue());
            Notification.show("Spieler gespeichert").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

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

        Button saveButton = new Button("Speichern", e ->
                eloService.enterResult1vs1(winnerSelect.getValue(), loserSelect.getValue(), loserGoals.getValue().shortValue()));


        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        addClassName("centered-content");

        add(spielername, button, winnerSelect, loserSelect, loserGoals, saveButton);
    }
}
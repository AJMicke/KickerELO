package org.kickerelo.kickerelo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.kickerelo.kickerelo.data.Spieler;
import org.kickerelo.kickerelo.repository.AuthentikUserRepository;
import org.kickerelo.kickerelo.repository.Ergebnis1vs1Repository;
import org.kickerelo.kickerelo.repository.Ergebnis2vs2Repository;
import org.kickerelo.kickerelo.repository.SpielerRepository;
import org.kickerelo.kickerelo.service.KickerEloService;
import org.kickerelo.kickerelo.util.AccessControlService;

@Route("profile")
public class ProfileView extends VerticalLayout {
    private final AccessControlService accessControlService;
    private final Ergebnis1vs1Repository ergebnis1vs1;
    private final Ergebnis2vs2Repository ergebnis2vs2;
    private final SpielerRepository spieler;
    private final KickerEloService eloService;
    private final AuthentikUserRepository userRepository;

    public ProfileView(Ergebnis1vs1Repository ergebnis1vs1, Ergebnis2vs2Repository ergebnis2vs2, SpielerRepository spielerRepository, AccessControlService accessControlService, KickerEloService eloService, AuthentikUserRepository userRepository) {
        this.ergebnis1vs1 = ergebnis1vs1;
        this.ergebnis2vs2 = ergebnis2vs2;
        this.spieler = spielerRepository;
        this.accessControlService = accessControlService;
        this.eloService = eloService;
        this.userRepository = userRepository;

        add(new H1("Profil"));
        accessControlService.getCurrentUser().ifPresentOrElse(user -> {
            Paragraph p1 = new Paragraph("Hallo, %s!".formatted(user.getName()));
            add(p1);

            user.getSpieler().ifPresentOrElse(
                    spieler -> {
                        var p2 = new Paragraph("Verbundene*r Spieler*in: %s".formatted(spieler.getName()));
                        Button buttonUnlink = new Button("Spieler*in trennen");
                        buttonUnlink.addClickListener(event -> {
                            spieler.setAuthentikUser(null);
                            user.setSpieler(null);
                            spielerRepository.save(spieler);
                            userRepository.save(user);
                            // TODO: Refresh
                        });
                        buttonUnlink.setDisableOnClick(true);
                        add(p2, new Paragraph("TODO: Hier könnten Stats angezeigt werden"), buttonUnlink);
                    },
                    () -> {
                        var p2 = new Paragraph("Kein*e Spieler*in verbunden.");

                        ComboBox<Spieler> playerSelect = new ComboBox<>("Neue*n Spieler*in verbinden");
                        playerSelect.setItems(eloService.getSpielerEntities());
                        playerSelect.setPlaceholder("Spieler auswählen");
                        Button submitButton = new Button("Speichern");
                        submitButton.addClickListener(event -> {
                            playerSelect.getOptionalValue().ifPresentOrElse(spieler -> {
                                if (spieler.getAuthentikUser().isPresent()) {
                                    submitButton.setEnabled(true);
                                    Notification.show("Spieler*in ist schon mit einem*einer User*in verbunden.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                                    return;
                                }
                                spieler.setAuthentikUser(user);
                                user.setSpieler(spieler);
                                spielerRepository.save(spieler);
                                userRepository.save(user);
                                //TODO: Refresh page
                            }, () -> {
                                submitButton.setEnabled(true);
                                Notification.show("Kein*e Spieler*in ausgewählt.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                            });
                        });
                        submitButton.setDisableOnClick(true);
                        var p3 = new Paragraph();
                        p3.add(playerSelect, submitButton);

                        add(p2, p3);
                    });
        }, () -> add(new Paragraph("Du musst dich einloggen, um dein Profil zu sehen.")));
    }
}

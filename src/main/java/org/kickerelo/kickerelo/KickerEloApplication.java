package org.kickerelo.kickerelo;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;


@SpringBootApplication
@EntityScan(basePackages = "org.kickerelo.kickerelo.data")
@EnableJpaRepositories
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@PWA(name = "Tischkicker-Elo", shortName = "KickerElo",
        description = "Erlaubt Hinzufügen von Spielständen und Tracking der Spieler Elos für 1 vs 1 und 2 vs 2")
public class KickerEloApplication implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(KickerEloApplication.class, args);
    }
}

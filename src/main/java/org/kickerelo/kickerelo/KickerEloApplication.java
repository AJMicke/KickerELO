package org.kickerelo.kickerelo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = "org.kickerelo.kickerelo.data")
@EnableJpaRepositories
@Theme("my-theme")
@PWA(name = "Tischkicker-Elo", shortName = "KickerElo",
        description = "Erlaubt Hinzufügen von Spielständen und Tracking der Spieler Elos für 1 vs 1 und 2 vs 2")
public class KickerEloApplication implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(KickerEloApplication.class, args);
    }

}

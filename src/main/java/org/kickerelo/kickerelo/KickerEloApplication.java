package org.kickerelo.kickerelo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = "org.kickerelo.kickerelo.data")
@EnableJpaRepositories
@Theme("my-theme")
public class KickerEloApplication implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(KickerEloApplication.class, args);
    }

}

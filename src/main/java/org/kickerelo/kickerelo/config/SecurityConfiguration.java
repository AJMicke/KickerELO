package org.kickerelo.kickerelo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Profile("prod")
@Configuration
class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/app/admin/**", "/app/admin", "/app/app/admin/**", "/app/app/admin").hasAuthority("Kicker Admin")
                .anyRequest().permitAll()
            )
            .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable());
    }
}

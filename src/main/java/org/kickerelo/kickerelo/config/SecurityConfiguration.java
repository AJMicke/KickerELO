package org.kickerelo.kickerelo.config;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Profile("prod")
@Configuration
class SecurityConfiguration extends VaadinWebSecurity {

    private static String rememberMeSecret = null;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (rememberMeSecret == null) rememberMeSecret = generateSecret();

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/app/admin/**", "/app/admin", "/app/app/admin/**", "/app/app/admin").hasAuthority("Kicker Admin")
                .anyRequest().permitAll())
            .rememberMe(rememberMe -> rememberMe.key(rememberMeSecret))
            .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable());
    }

    private String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

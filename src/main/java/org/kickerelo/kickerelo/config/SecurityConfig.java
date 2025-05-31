package org.kickerelo.kickerelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/app/admin/**").authenticated() // Nur authentifizierte User
            .anyRequest().permitAll()
        )
            .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
            // .oauth2Login(oauth -> oauth
            //     .defaultSuccessUrl("/app/app/admin", true)
            // )
            //.and()
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}

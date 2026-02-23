package org.kickerelo.kickerelo.config;

import com.vaadin.flow.spring.security.NavigationAccessControlConfigurer;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Profile("test")
@EnableWebSecurity
@Configuration
public class TestSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.with(VaadinSecurityConfigurer.vaadin(),
                configurer -> configurer.anyRequest(AuthorizeHttpRequestsConfigurer.AuthorizedUrl::permitAll));

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public static NavigationAccessControlConfigurer navigationAccessControlConfigurer() {
        return new NavigationAccessControlConfigurer().disabled();
    }
}

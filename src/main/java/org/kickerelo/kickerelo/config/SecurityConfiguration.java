package org.kickerelo.kickerelo.config;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@Profile("prod")
@Configuration
class SecurityConfiguration {

    private final OAuth2AuthorizedClientService authorizedClientService;

    // Inject the persistent authorized client service we configured previously
    public SecurityConfiguration(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    private static String rememberMeSecret = null;

    protected void configure(HttpSecurity http) throws Exception {
        if (rememberMeSecret == null) rememberMeSecret = generateSecret();

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/app/admin/**", "/app/admin", "/app/app/admin/**", "/app/app/admin").hasAuthority("Kicker Admin")
                .anyRequest().permitAll())
            .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable());
    }


    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/admin/**", "/app/admin", "/app/app/admin/**", "/app/app/admin").hasAuthority("Kicker Admin")
                        .anyRequest().permitAll())
                .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    private String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

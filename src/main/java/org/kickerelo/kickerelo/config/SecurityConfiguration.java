package org.kickerelo.kickerelo.config;

import org.kickerelo.kickerelo.data.AuthentikUser;
import org.kickerelo.kickerelo.repository.AuthentikUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Profile("prod")
@Configuration
class SecurityConfiguration {

    AuthentikUserRepository userRepository;

    public SecurityConfiguration(AuthentikUserRepository userRepository) {
        this.userRepository = userRepository;
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
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/admin/**", "/app/admin", "/app/app/admin/**", "/app/app/admin").hasAuthority("Kicker Admin")
                        .anyRequest().permitAll())
                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> {
                            String id = ((OAuth2User) authentication.getPrincipal()).getAttribute("sub");
                            if (!userRepository.existsById(id)) {
                                String name = ((OAuth2User) authentication.getPrincipal()).getAttribute("name");
                                AuthentikUser user = new AuthentikUser(id, name);
                                userRepository.save(user);
                            }
                            response.sendRedirect("/");
                        }))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            JdbcTemplate jdbcTemplate,
            ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }
}

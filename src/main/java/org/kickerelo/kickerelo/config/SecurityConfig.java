package org.kickerelo.kickerelo.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration oidcRegistration = ClientRegistration.withRegistrationId("oidc")
            .clientId(dotenv.get("OIDC_CLIENT_ID"))
            .clientSecret(dotenv.get("OIDC_CLIENT_SECRET"))
            .scope("openid", "profile", "email")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationUri(dotenv.get("OIDC_ISSUER_BASE_URI") + "application/o/authorize/")
            .tokenUri(dotenv.get("OIDC_ISSUER_BASE_URI") + "application/o/token/")
            .userInfoUri(dotenv.get("OIDC_ISSUER_BASE_URI") + "application/o/userinfo/")
            .userNameAttributeName("sub")
            .clientName("OIDC")
            .redirectUri(dotenv.get("OIDC_REDIRECT_URI"))
            .build();

        return new InMemoryClientRegistrationRepository(oidcRegistration);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/oauth2/**").permitAll()  // Allow all OAuth2 requests
                .anyRequest().permitAll()  // Allow all requests
            )
            .oauth2Login()
            .and()
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}

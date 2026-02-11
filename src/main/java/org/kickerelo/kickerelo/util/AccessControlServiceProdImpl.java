package org.kickerelo.kickerelo.util;

import org.jetbrains.annotations.NotNull;
import org.kickerelo.kickerelo.data.AuthentikUser;
import org.kickerelo.kickerelo.repository.AuthentikUserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Profile("prod")
public class AccessControlServiceProdImpl implements AccessControlService {
    private final AuthentikUserRepository userRepository;

    public AccessControlServiceProdImpl(AuthentikUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean userAllowedForRole(String role) {
        // Check if authentication is present
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof OidcUser oidcUser)) return false;

        // Empty String means there just needs to be authentication, not a specific group
        if (role.isEmpty()) return true;

        // Get the list of groups the user is part of
        Object groupsObj = oidcUser.getClaims().getOrDefault("groups", List.of());
        if (!(groupsObj instanceof List<?>)) return false;

        // Keep only Strings in the list
        List<String> listOfGroups = ((List<?>) groupsObj).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();

        // Check if the user is part of the required group
        return listOfGroups.contains(role);
    }

    @NotNull
    @Override
    public Optional<AuthentikUser> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof OidcUser oidcUser)) {
            return Optional.empty();
        }
        String id = oidcUser.getAttribute("sub");
        if (id == null) {
            throw new RuntimeException("Couldn't find sub attribute on current user.");
        }
        Optional<AuthentikUser> authentikUser = userRepository.findById(id);
        if (authentikUser.isEmpty()) {
            throw new RuntimeException("User is authenticated but doesn't exist in database.");
        }
        return authentikUser;
    }
}

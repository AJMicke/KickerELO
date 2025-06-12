package org.kickerelo.kickerelo.util;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("prod")
public class AccessControlServiceProdImpl implements AccessControlService {
    @Override
    public boolean userAllowedForRole(String role) {
        // Check if authentication is present
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof OidcUser oidcUser)) return false;

        // Empty String means there just needs to be authentication, not a specific group
        if (role.isEmpty()) return true;

        // Get the list of groups the user is part of
        Object groupsObj = oidcUser.getClaims().getOrDefault("groups", List.of());
        if (!(groupsObj instanceof  List<?>)) return false;

        // Keep only Strings in the list
        List<String> listOfGroups = ((List<?>) groupsObj).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();

        // Check if the user is part of the required group
        return listOfGroups.contains(role);
    }
}

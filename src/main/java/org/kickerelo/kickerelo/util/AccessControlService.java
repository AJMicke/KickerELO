package org.kickerelo.kickerelo.util;

import org.kickerelo.kickerelo.data.AuthentikUser;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface AccessControlService {
    boolean userAllowedForRole(String role);

    @Nonnull
    Optional<AuthentikUser> getCurrentUser();
}

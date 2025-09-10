package org.kickerelo.kickerelo.util;

import org.jetbrains.annotations.NotNull;
import org.kickerelo.kickerelo.data.AuthentikUser;
import org.kickerelo.kickerelo.repository.AuthentikUserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("test")
public class AccessControlServiceTestImpl implements AccessControlService {
    private final AuthentikUserRepository userRepository;

    public AccessControlServiceTestImpl(AuthentikUserRepository userRepository) {
        this.userRepository = userRepository;
        userRepository.save(getCurrentUser().orElseThrow()); // Ensure test user exists in DB;
    }

    @Override
    public boolean userAllowedForRole(String role) {
        return true;
    }

    @NotNull
    @Override
    public Optional<AuthentikUser> getCurrentUser() {
        Optional<AuthentikUser> user = userRepository.findById("test_profile_user");
        return Optional.of(user.orElseGet(() -> new AuthentikUser("test_profile_user", "Test Profile User")));
    }
}

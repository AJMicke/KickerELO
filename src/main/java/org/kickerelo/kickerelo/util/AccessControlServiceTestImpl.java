package org.kickerelo.kickerelo.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class AccessControlServiceTestImpl implements AccessControlService {
    @Override
    public boolean userAllowedForRole(String role) {
        return true;
    }
}

package org.kickerelo.kickerelo.repository;

import org.kickerelo.kickerelo.data.AuthentikUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthentikUserRepository extends JpaRepository<AuthentikUser, String> {
}

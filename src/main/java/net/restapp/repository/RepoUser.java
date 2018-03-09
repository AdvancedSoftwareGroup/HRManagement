package net.restapp.repository;

import net.restapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoUser extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

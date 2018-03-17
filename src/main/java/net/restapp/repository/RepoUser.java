package net.restapp.repository;

import net.restapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoUser extends JpaRepository<User, Long> {

    /**
     * Find user by email
     * @param email - user's email
     * @return - user
     */
    User findByEmail(String email);

    /**
     * Find all users by role ID
     * @param id - role ID
     * @return - list users
     */
    @Query(value = "SELECT * FROM users WHERE role_id = ?1", nativeQuery = true)
    List<User> findAllByRoleId(Long id);
}

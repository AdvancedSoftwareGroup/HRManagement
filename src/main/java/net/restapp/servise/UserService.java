package net.restapp.servise;

import net.restapp.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void delete(Long id);

    List<User> getAll();

    User getById(Long id);

    User findByEmail(String username);
}

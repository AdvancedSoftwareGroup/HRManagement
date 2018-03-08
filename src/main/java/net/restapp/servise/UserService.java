package net.restapp.servise;

import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.User;

import java.util.List;

public interface UserService {

    void save(User user) throws EntityAlreadyExistException;

    void delete(Long id) throws Exception;

    List<User> getAll();

    User getById(Long id);

    User findByEmail(String username);
}

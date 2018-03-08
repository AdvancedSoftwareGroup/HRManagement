package net.restapp.servise;

import net.restapp.model.Role;

import java.util.List;

public interface RoleService {

    void save(Role role) throws Exception;

    void delete(Long id) throws Exception;

    List<Role> getAll();

    Role getById(Long id);
}

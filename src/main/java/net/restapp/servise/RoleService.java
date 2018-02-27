package net.restapp.servise;

import net.restapp.model.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);

    void delete(Long id);

    List<Role> getAll();

    Role getById(Long id);
}

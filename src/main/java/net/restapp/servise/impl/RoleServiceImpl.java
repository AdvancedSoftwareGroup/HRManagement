package net.restapp.servise.impl;

import lombok.extern.slf4j.Slf4j;
import net.restapp.model.Role;
import net.restapp.repository.RepoRole;
import net.restapp.servise.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * The service's layer of application for Role.
 */
@Service
@Slf4j
public class RoleServiceImpl implements IService<Role> {


    /**
     * The field of Role repository's layer that is called for use it's methods
     */
    @Autowired
    RepoRole repoRole;

    /**
     * The method calls a repository's method for save an role
     * cant change roles with id < 4 ({ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR})
     */
    @Override
    public void save(Role role) {
        if (role != null) {
            if (role.getId() < 4 && role.getId() != 0) throw new IllegalArgumentException("you cant change role with id <=3");
        }
        repoRole.save(role);
        log.info("In save role");
    }

    /**
     * The method calls a repository's method for delete an role by Id
     * cant change roles with id < 4 ({ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR})
     */
    @Override
    public void delete(Long id) {
        if (id < 4  && id != 0) throw new IllegalArgumentException("you cant delete role with id <=3");
        log.info("In deleting role by ID{}",id);
        repoRole.delete(id);
    }

    /**
     * The method calls a repository's method for get all roles
     */
    @Override
    public List<Role> getAll() {
        log.info("In getAll roles");
        return repoRole.findAll();
    }

    /**
     * The method calls a repository's method for gat an role by Id
     */
    @Override
    public Role getById(Long id) {
        log.info("In getById getting role by ID {}",id);
        return repoRole.findOne(id) ;
    }
}

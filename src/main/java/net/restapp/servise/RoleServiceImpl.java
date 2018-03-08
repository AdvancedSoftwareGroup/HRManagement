package net.restapp.servise;

import lombok.extern.slf4j.Slf4j;
import net.restapp.model.Role;
import net.restapp.repository.RepoRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RepoRole repoRole;

    @Override
    public void save(Role role) throws Exception {
        if (role.getId() < 4) throw new Exception("cant change role with id <=3");
        repoRole.save(role);
        log.info("In save ");
    }

    @Override
    public void delete(Long id) throws Exception {
        if (id < 4) throw new Exception("cant delete role with id <=3");
        log.info("In deleting role by ID{}",id);
        repoRole.delete(id);
    }

    @Override
    public List<Role> getAll() {
        log.info("In getAll");
        return repoRole.findAll();
    }

    @Override
    public Role getById(Long id) {
        log.info("In getById getting role by ID {}",id);
        return repoRole.findOne(id) ;
    }
}

package net.restapp.servise;

import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.User;
import net.restapp.repository.RepoRole;
import net.restapp.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RepoUser repoUser;

    @Autowired
    RepoRole repoRole;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) throws EntityAlreadyExistException {
        if (user.getId() == 0){
            if (emailExist(user.getEmail())) {
                throw new EntityAlreadyExistException(
                        "There is an account with that email address:" + user.getEmail() +" exist at database");
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRole() == null){
            user.setRole(repoRole.getOne(2L));
        }

        repoUser.save(user);
    }
    @Override
    public void delete(Long id) throws Exception {
        if (id == 1) throw new Exception("cant delete user with id=1");
        repoUser.delete(id);
    }

    @Override
    public List<User> getAll() {
        return repoUser.findAll();
    }

    @Override
    public User getById(Long id) {
        return repoUser.findOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return repoUser.findByEmail(email);
    }


    private boolean emailExist(String email){
        User user = repoUser.findByEmail(email);
        return  (user != null);
    }
}

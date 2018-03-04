package net.restapp.servise;

import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.User;
import net.restapp.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RepoUser repoUser;

    @Override
    public void save(User user) {
        User user1 = repoUser.findByEmail(user.getEmail());
        if (user1 != null){
            throw new EntityAlreadyExistException("User with email="+user.getEmail()+" already exist");
        }
        repoUser.save(user);
    }

    @Override
    public void delete(Long id) {
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

}

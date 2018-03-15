package net.restapp.servise.impl;


import net.restapp.dto.UserUpdateEmailDTO;
import net.restapp.dto.UserUpdatePasswordDTO;
import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.Role;
import net.restapp.model.User;
import net.restapp.repository.RepoRole;
import net.restapp.repository.RepoUser;
import net.restapp.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
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
            //when employee add to the system password=11111. Then employee change pass by himself
            user.setPassword("11111111");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRole() == null){
            user.setRole(repoRole.getOne(2L));
        }

        repoUser.save(user);
    }

    @Override
    public void delete(Long id) {
        if (id == 1) throw new IllegalArgumentException("cant delete user with id=1");
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

    @Override
    public List<User> getAllByRoleId(Long id) {
        return repoUser.findAllByRoleId(id);
    }

    /**
     * The method change password and calls a repository's method for save a user
    */
    @Override
    @Transactional
    public void updateUserPasswordById(Long userId, UserUpdatePasswordDTO dto) {
        User user = repoUser.findOne(userId);
        if (user == null) {
            throw  new EntityNotFoundException(String.format("There is no User with email: %s", userId));
        }
        if (bCryptPasswordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        } else {
            throw new BadCredentialsException("Old password is wrong");
        }
        repoUser.save(user);

    }

    /**
     * The method change email and calls a repository's method for save a user
    */
    @Override
    @Transactional
    public void updateUserEmailById(Long userId, UserUpdateEmailDTO dto) {
        User user = repoUser.findOne(userId);
        if (user == null) {
            throw  new EntityNotFoundException(String.format("There is no User with email: %s", userId));
        }
        if (user.getEmail().equals(dto.getOld_email())) {
            user.setEmail(dto.getEmail());
        } else {
            throw new BadCredentialsException("Old email is wrong");
        }
        repoUser.save(user);

    }

    /**
     * The method change role and calls a repository's method for save a user
     */
    @Override
    @Transactional
    public void updateUserRole(User user, Long roleId) throws AccessDeniedException {
        if (user.getId() == 1) {
            throw new AccessDeniedException("you can't change role for user with id=1");
        }
        Role role = repoRole.findOne(roleId);
        if (role == null) {
            throw  new EntityNotFoundException(String.format("There is no role with roleId: %s", roleId));
        }
        user.setRole(role);
        repoUser.save(user);
    }


    /**
     * Check if email already exist at the database
     * @return true if email exist and false if not
     */
    private boolean emailExist(String email){
        User user = repoUser.findByEmail(email);
        return  (user != null);
    }

    /**
     * Return true if login user corresponds to employeeId
     */
    public boolean checkLoginUserHavePetitionForThisInfo(Long employeeId, HttpServletRequest request) {
        String email=request.getUserPrincipal().getName();
        User user = findByEmail(email);
        return user.getId() != employeeId;
    }
}

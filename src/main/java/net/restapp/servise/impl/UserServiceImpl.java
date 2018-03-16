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

/**
 * The service's layer of application for User, contains CRUD methods
 */

@Service
public class UserServiceImpl implements UserService {

    /**
     * The field of User repository's layer that is called for use it's methods
     */
    @Autowired
    RepoUser repoUser;

    /**
     * The field of Role repository's layer that is called for use it's methods
     */
    @Autowired
    RepoRole repoRole;

    /**
     * The field of BCryptPasswordEncoder that is use for crypt password
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The method calls a repository's method for save a User
     * @param user - user
     * @throws EntityAlreadyExistException - send if user with email already exist at database
     */
    @Override
    public void save(User user) throws EntityAlreadyExistException {
        if (user != null) {
            addInfoForNewUser(user);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            if (user.getRole() == null) {
                user.setRole(repoRole.getOne(2L));
            }
        }
        repoUser.save(user);
    }

    /**
     * check that user's email didn't exist and set password
     * @param user - user
     */
    private void addInfoForNewUser(User user) {
        if (user.getId() != 0) return;
        if (emailExist(user.getEmail())) {
            throw new EntityAlreadyExistException(
                    "There is an account with that email address:" + user.getEmail() + " exist at database");
        }
        //when employee add to the system password=11111. Then employee change pass by himself
        user.setPassword("11111111");
    }

    /**
     * The method calls a repository's method for delete a user by ID.
     *
     * @param id - user's ID
     */
    @Override
    public void delete(Long id) {
        if (id != null) {
            if (id == 1) throw new IllegalArgumentException("cant delete user with id=1");
        }
        repoUser.delete(id);
    }

    /**
     * The method calls a repository's method for get all users
     *
     * @return - list of user
     */
    @Override
    public List<User> getAll() {
        return repoUser.findAll();
    }

    /**
     * The method calls a repository's method for get one user by ID
     *
     * @param id - user's ID
     * @return - user
     */
    @Override
    public User getById(Long id) {
        return repoUser.findOne(id);
    }

    /**
     * The method calls a repository's method for gat a user by email
     *
     * @param email - user's email
     * @return - user
     */
    @Override
    public User findByEmail(String email) {
        return repoUser.findByEmail(email);
    }

    /**
     * The method calls a repository's method for get all user by roleId
     *
     * @param id - role's ID
     * @return - list users
     */
    @Override
    public List<User> getAllByRoleId(Long id) {
        return repoUser.findAllByRoleId(id);
    }


    /**
     * The method change password and calls a repository's method for save a user
     *
     * @param userId - user's ID
     * @param dto    - {@link net.restapp.dto.UserUpdatePasswordDTO}
     */
    @Override
    @Transactional
    public void updateUserPasswordById(Long userId, UserUpdatePasswordDTO dto) {
        User user = repoUser.findOne(userId);
        if (user == null) {
            throw new EntityNotFoundException(String.format("There is no User with email: %s", userId));
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
     *
     * @param userId user's ID
     * @param dto    - {@link net.restapp.dto.UserUpdateEmailDTO}
     */
    @Override
    @Transactional
    public void updateUserEmailById(Long userId, UserUpdateEmailDTO dto) {
        User user = repoUser.findOne(userId);
        if (user == null) {
            throw new EntityNotFoundException(String.format("There is no User with email: %s", userId));
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
     *
     * @param user   - user
     * @param roleId - role's ID
     * @throws IllegalArgumentException - send if try change role for user with id=1 (it's main ADMIN user)
     */
    @Override
    @Transactional
    public void updateUserRole(User user, Long roleId) {
        if (user.getId() == 1) {
            throw new IllegalArgumentException("you can't change role for user with id=1");
        }
        Role role = repoRole.findOne(roleId);
        if (role == null) {
            throw new EntityNotFoundException(String.format("There is no role with roleId: %s", roleId));
        }
        user.setRole(role);
        repoUser.save(user);
    }


    /**
     * Check if email already exist at the database
     *
     * @param email - email
     * @return true if email exist and false if not
     */
    private boolean emailExist(String email) {
        User user = repoUser.findByEmail(email);
        return (user != null);
    }


    /**
     * Check is user from HttpServletRequest have employee id equals employeeId from arguments of the method
     *
     * @param employeeId - employee's id
     * @param request    -
     * @return true if employeedId equals employeeId from request, and false in another case
     */
    public boolean checkLoginUserHavePetitionForThisInfo(Long employeeId, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = findByEmail(email);
        return user.getId() != employeeId;
    }
}

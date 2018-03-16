package net.restapp.servise;

import net.restapp.dto.UserUpdateEmailDTO;
import net.restapp.dto.UserUpdatePasswordDTO;
import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.User;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * Interface for service's layer of User. Extends CRUD methods from {@link net.restapp.servise.IService}
 */

public interface UserService extends IService<User>{

    /**
     * The method calls a repository's method for gat a user by email
     * @param email - user's email
     * @return - user
     */
    User findByEmail(String email);

    /**
     * The method calls a repository's method for get all user by roleId
     * @param id - role's ID
     * @return - list users
     */
    List<User> getAllByRoleId(Long id);

    /**
     * The method change password and calls a repository's method for save a user
     * @param userId - user's ID
     * @param dto - {@link net.restapp.dto.UserUpdatePasswordDTO}
     */
    void updateUserPasswordById(Long userId, UserUpdatePasswordDTO dto);

    /**
     * The method change email and calls a repository's method for save a user
     * @param userId user's ID
     * @param dto - {@link net.restapp.dto.UserUpdateEmailDTO}
     */
    void updateUserEmailById(Long userId, UserUpdateEmailDTO dto);


    /**
     * The method change role and calls a repository's method for save a user
     * @param user - user
     * @param roleId - role's ID
     * @throws AccessDeniedException - send if try change role for user with id=1 (it's main ADMIN user)
     */
    void updateUserRole(User user, Long roleId);

    /**
     * Check is user from HttpServletRequest have employee id equals employeeId from arguments of the method
     * @param employeeId - employee's id
     * @param request -
     * @return true if employeedId equals employeeId from request, and false in another case
     */
    boolean checkLoginUserHavePetitionForThisInfo(Long employeeId, HttpServletRequest request);
}

package net.restapp.servise;

import net.restapp.dto.UserUpdateEmailDTO;
import net.restapp.dto.UserUpdatePasswordDTO;
import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.User;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {

    void save(User user) throws EntityAlreadyExistException;

    void delete(Long id) throws Exception;

    List<User> getAll();

    User getById(Long id);

    User findByEmail(String username);


    List<User> getAllByRoleId(Long id);

    void updateUserPasswordById(Long userId, UserUpdatePasswordDTO dto);

    void updateUserEmailById(Long userId, UserUpdateEmailDTO dto);

    void updateUserRole(User user, Long roleId) throws AccessDeniedException;

    boolean checkLoginUserHavePetitionForThisInfo(Long employeeId, HttpServletRequest request);
}

package net.restapp.service;

import net.restapp.dto.UserReadDTO;
import net.restapp.dto.UserUpdateEmailDTO;
import net.restapp.dto.UserUpdatePasswordDTO;
import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.Role;
import net.restapp.model.Status;
import net.restapp.model.User;
import net.restapp.repository.RepoRole;
import net.restapp.repository.RepoUser;
import net.restapp.servise.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    /**
     * The repository's layer object
     */
    @MockBean
    RepoUser repoUser;

    /**
     * The repository's layer object
     */
    @MockBean
    RepoRole repoRole;

    /**
     * The field of BCryptPasswordEncoder that is use for crypt password
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The service's layer object
     */
    @Autowired
    UserService userService;

    private User user;
    private UserUpdatePasswordDTO userPassDto;
    private UserUpdateEmailDTO userUpdateEmailDTO;

    @Before
    public void init (){
        user = new User();
        user.setEmail("email_1");
        user.setId(2L);
        String pass = bCryptPasswordEncoder.encode("111");
        user.setPassword(pass);

        Mockito.when(repoUser.findByEmail(user.getEmail()))
                .thenReturn(user);

        Mockito.when(repoRole.getOne(2L))
                .thenReturn(new Role());


        userPassDto = new UserUpdatePasswordDTO();
        userPassDto.setPassword("2222");
        userPassDto.setOldPassword(pass);
        userPassDto.setConfirmPassword(userPassDto.getPassword());

        Mockito.when(repoUser.findOne(user.getId()))
                .thenReturn(user);

        userUpdateEmailDTO = new UserUpdateEmailDTO();
        userUpdateEmailDTO.setOld_email(user.getEmail());
        userUpdateEmailDTO.setEmail("new email");
        userUpdateEmailDTO.setConfirmEmail(userUpdateEmailDTO.getEmail());
    }
    /**
     * The test-method for same-named service's default method that save entity.
     * Try save null entity
     */
    @Test
    public void saveNull(){
        trySave(null);
    }

    /**
     * The test-method for same-named service's default method that save entity.
     * Save new User with already exist email
     */
    @Test(expected = EntityAlreadyExistException.class)
    public void saveWithExistEmail()  {
        user.setId(0);
        trySave(user);
    }

    /**
     * The test-method for same-named service's default method that save entity.
     * Save new User
     */
    @Test
    public void saveWithoutExistEmail()  {
        User user = new User();
        user.setEmail("email_2");
        user.setId(0);
        trySave(user);

        Mockito.when(repoUser.findByEmail("email_2"))
                .thenReturn(null);

        boolean match = bCryptPasswordEncoder.matches("11111111", user.getPassword());
        assertEquals(match,true);
        assertNotEquals(user.getRole(),null);
    }
    /**
     * The test-method for same-named service's default method that save entity.
     * Save edit User
     */
    @Test
    public void save(){
        User user = new User();
        String pass = "eeee";
        user.setPassword(pass);
        user.setId(2);
        trySave(user);

        boolean match = bCryptPasswordEncoder.matches(pass, user.getPassword());
        assertEquals(match,true);
        assertNotEquals(user.getRole(),null);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete unchanged entity
     */
    @Test(expected = IllegalArgumentException.class)
    public void deleteDefaultStatus() {
        tryDelete(1L);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete entity
     */
    @Test
    public void delete() {
        tryDelete(6L);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete entity with id = null
     */
    @Test
    public void deleteNullID() {
        tryDelete(null);
    }

    /**
     * The test-method for same-named service's method that find User by Email.
     */
    @Test
    public void findByEmail(){

        User found = userService.findByEmail(user.getEmail());
        User found2 = userService.findByEmail("new email");

        assertEquals(found,user);
        assertEquals(found2,null);
    }

    /**
     * The test-method for updateUserPasswordById service's method that change password for User.
     * expected exception because send null user
     */
    @Test (expected = EntityNotFoundException.class)
    public void updateUserPasswordById_withNullUser(){
        User user1 = new User();
        user1.setId(3L);
        when(repoUser.findOne(user1.getId())).thenReturn(null);

        UserUpdatePasswordDTO dto = new UserUpdatePasswordDTO();

        userService.updateUserPasswordById(user1.getId(),dto);
    }

    /**
     * The test-method for updateUserPasswordById service's method that change password for User.
     * expected exception because send wrong old password
     */
    @Test (expected = BadCredentialsException.class)
    public void updateUserPasswordById_with_diff_pass(){

        userPassDto.setOldPassword("sdsdsd");

        userService.updateUserPasswordById(user.getId(),userPassDto);

        boolean match = bCryptPasswordEncoder.matches(user.getPassword(), userPassDto.getPassword());
        assertEquals(match,false);
    }
    /**
     * The test-method for same-named service's method that change password for User.
     */
    @Test
    public void updateUserPasswordById(){

        userService.updateUserPasswordById(user.getId(),userPassDto);

        boolean match = bCryptPasswordEncoder.matches(user.getPassword(), userPassDto.getPassword());
        assertEquals(match,true);
    }

    /**
     * The test-method for same-named service's method that change email for User.
     */
    @Test
    public void updateUserEmailById(){

        userService.updateUserEmailById(user.getId(),userUpdateEmailDTO);

        assertEquals(user.getEmail(),userUpdateEmailDTO.getEmail());
    }

    /**
     * The test-method for updateUserPasswordById service's method that change password for User.
     * expected exception because send null user
     */
    @Test (expected = EntityNotFoundException.class)
    public void updateUserEmailById_withNullUser(){
        User user1 = new User();
        user1.setId(3L);
        when(repoUser.findOne(user1.getId())).thenReturn(null);

        UserUpdateEmailDTO dto = new UserUpdateEmailDTO();

        userService.updateUserEmailById(user1.getId(),dto);
    }

    /**
     * The test-method for updateUserPasswordById service's method that change password for User.
     * expected exception because send wrong old password
     */
    @Test (expected = BadCredentialsException.class)
    public void updateUserEmailById_with_wrong_oldEmail(){

        userUpdateEmailDTO.setOld_email("sdsdsd");

        userService.updateUserEmailById(user.getId(),userUpdateEmailDTO);

        assertEquals(user.getEmail(),userUpdateEmailDTO.getEmail());
    }

    /**
     * The test-method for updateUserRole service's method that change role for User.
     * expected exception because try change role for default user
     */
    @Test(expected = IllegalArgumentException.class)
    public void updateUserRole_forDefaulUser(){
        User user = new User();
        user.setId(1);
        userService.updateUserRole(user,3L);
    }

    /**
     * The test-method for updateUserRole service's method that change role for User.
     * expected exception because try change role for not exist
     */
    @Test(expected = EntityNotFoundException.class)
    public void updateUserRoleForNotExistingRole(){
        Long roleId = 6L;
        Mockito.when(repoRole.findOne(roleId))
                .thenReturn(null);
        userService.updateUserRole(user,roleId);
    }

    /**
     * The test-method for updateUserRole service's method that change role for User.
     */
    @Test
    public void updateUserRole(){
        Long roleId = 7L;
        Mockito.when(repoRole.findOne(roleId))
                .thenReturn(new Role());
        userService.updateUserRole(user,roleId);
    }



    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete entity
     * @param id - status's id
     */
    private void tryDelete(Long id) {
        // Arrange
        doNothing().when(repoUser).delete(id);
        // Act
        userService.delete(id);
        // Assert
        verify(repoUser, times(1)).delete(id);
    }

   /**
     * The test-method for same-named service's default method that save entity.
     * try save entity
     * @param user  - user
     */
    private void trySave(User user) {
        // Arrange
        when(repoUser.save(user)).thenReturn(user);
        // Act
        userService.save(user);
        // Assert
        verify(repoUser, times(1)).save(user);
    }
}

package net.restapp.service;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Before
    public void init (){
        User user = new User();
        user.setEmail("email_1");

        Mockito.when(repoUser.findByEmail("email_1"))
                .thenReturn(user);

        Mockito.when(repoUser.findByEmail("email_2"))
                .thenReturn(null);

        Mockito.when(repoRole.getOne(2L))
                .thenReturn(new Role());


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
        User user = new User();
        user.setEmail("email_1");
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
        user.setPassword("eeeee");
        user.setId(5);
        trySave(user);

        boolean match = bCryptPasswordEncoder.matches("11111111", user.getPassword());
        assertEquals(match,false);
        assertEquals(user.getRole(),null);
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
        User user = new User();
        user.setEmail("email_1");

        User found = userService.findByEmail(user.getEmail());
        User found2 = userService.findByEmail("new email");

        assertEquals(found,user);
        assertEquals(found2,null);
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

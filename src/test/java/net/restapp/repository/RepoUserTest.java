package net.restapp.repository;

import net.restapp.model.Role;
import net.restapp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepoUserTest {
    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoUser repoUser;


    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        User user = createUser();

        entityManager.persist(user);
        entityManager.flush();

        User actualEvent = repoUser.findOne(user.getId());

        assertEquals(user, actualEvent);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

        Long id = null;
        repoUser.findOne(id);

    }

    /**
     * The test-method for findByName repository's default method that get one entity by name
     */
    @Test
    public void findByEmail(){
        User user = createUser();

        entityManager.persist(user);
        entityManager.flush();

        User actualEvent = repoUser.findByEmail(user.getEmail());

        assertEquals(user, actualEvent);
    }

    /**
     * The test-method for findAllByRoleId repository's method that get list entity by role ID
     */
    @Test
    public void findAllByRoleId(){
        User user = createUser();
        List<User> list = Arrays.asList(user);
        entityManager.persist(user);
        entityManager.flush();

        List<User> actualEvent = repoUser.findAllByRoleId(user.getRole().getId());

        assertEquals(list, actualEvent);
    }

    /**
     * The test-method for findAllByRoleId repository's method that get list entity by role ID
     * roleId=null
     */
    @Test
    public void findAllByNullRoleId(){
        List<User> actualEvent = repoUser.findAllByRoleId(null);

        assertEquals(actualEvent.isEmpty(), true);
    }

    /**
     * Generate User
     */
    private User createUser(){
        Role role = new Role();
        role.setName("role 1");
        entityManager.persist(role);

        User user = new User();
        user.setEmail("sveta@gmail.com");
        user.setPassword("pass");
        user.setRole(role);
        return user;
    }
}

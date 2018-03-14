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
     * Generate User
     */
    private User createUser(){
        User user = new User();
        user.setEmail("sveta@gmail.com");
        user.setPassword("pass");
        Role role = new Role();
        role.setName("role 1");
        user.setRole(role);
        return user;
    }
}

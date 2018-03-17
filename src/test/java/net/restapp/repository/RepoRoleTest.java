package net.restapp.repository;

import net.restapp.model.Role;
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
public class RepoRoleTest {

    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoRole repoRole;

    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        Role role = new Role();
        role.setName("Role 1");

        entityManager.persist(role);
        entityManager.flush();

        Role actualRole = repoRole.findOne(role.getId());

        assertEquals(role, actualRole);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

        Long id = null;
        repoRole.findOne(id);

    }

}

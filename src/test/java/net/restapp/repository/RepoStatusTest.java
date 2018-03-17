package net.restapp.repository;

import net.restapp.model.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepoStatusTest {
    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoStatus repoStatus;

    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        Status status = new Status();
        status.setName("Status 1");
        status.setSalary_coef(BigDecimal.valueOf(2.6));

        entityManager.persist(status);
        entityManager.flush();

        Status actualEvent = repoStatus.findOne(status.getId());

        assertEquals(status, actualEvent);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

        Long id = null;
        repoStatus.findOne(id);

    }

    /**
     * The test-method for findByName repository's default method that get one entity by name
     */
    @Test
    public void findByName(){
        Status status = new Status();
        status.setName("Status 1");
        status.setSalary_coef(BigDecimal.valueOf(2.6));

        entityManager.persist(status);
        entityManager.flush();

        Status actualEvent = repoStatus.findByName(status.getName());

        assertEquals(status, actualEvent);
    }

}

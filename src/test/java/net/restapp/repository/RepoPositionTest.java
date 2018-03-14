package net.restapp.repository;

import net.restapp.model.Department;
import net.restapp.model.Position;
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
public class RepoPositionTest {
    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoPosition repoPosition;

    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        Department department = new Department();
        department.setName("Department 1");
        Position position = new Position();
        position.setDepartment(department);
        position.setDayForVacation(12);
        position.setName("position 1");
        position.setSalary(BigDecimal.valueOf(12.23));

        entityManager.persist(position);
        entityManager.flush();

        Position actualPosition = repoPosition.findOne(position.getId());

        assertEquals(position, actualPosition);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

        Long id = null;
        repoPosition.findOne(id);

    }

}

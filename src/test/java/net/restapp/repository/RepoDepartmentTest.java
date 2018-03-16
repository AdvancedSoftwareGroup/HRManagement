package net.restapp.repository;

import net.restapp.model.Department;
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
public class RepoDepartmentTest {

    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoDepartment repoDepartment;

    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        Department department = new Department();
        department.setName("Department 1");
//        Department department2 = new Department();
//        department2.setName("Department 2");

        entityManager.persist(department);
 //       entityManager.persist(department2);
        entityManager.flush();

        Department actualDepartment = repoDepartment.findOne(department.getId());

        assertEquals(department, actualDepartment);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

       Long id = null;
       repoDepartment.findOne(id);

    }

    //todo: how done this. We must get exception in this case
    /**
     * The test-method for same-named repository's default method that delete one entity by ID.
     */
//    @Test
//    public void delete(){
//        Department department = new Department();
//        department.setName("Department 1");
//        Position position = new Position();
//        position.setDayForVacation(12);
//        position.setDepartment(department);
//        position.setName("position 1");
//        position.setSalary(BigDecimal.valueOf(12323));
//        List<Position> list = Arrays.asList(position);
//        department.setPositions(list);
//
//        entityManager.persist(department);
//        entityManager.flush();
//        repoDepartment.delete(department.getId());
//    }


}

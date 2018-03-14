package net.restapp.repository;

import net.restapp.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepoEmployeesTest {

    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoEmployees repoEmployees;

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

        Long id = null;
        repoEmployees.findOne(id);

    }

    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        Employees employees = createEmployee(false);

        entityManager.persist(employees);
        entityManager.flush();

        Employees actualEmployees = repoEmployees.findOne(employees.getId());

        assertEquals(employees, actualEmployees);
    }

    /**
     * The test-method for save repository's default method that try save Employee with User=null.
     */
    @Test(expected = ConstraintViolationException.class)
    public void saveEmployeeWithNullUser() {
        Employees employees = createEmployee(true);

        entityManager.persist(employees);
        entityManager.flush();
        repoEmployees.save(employees);

    }

    /**
     * The test-method for same-name repository's method that getAll entity with positionId
     */
    @Test
    public void findAllWithPositionId() {
        Employees employees = createEmployeeWithPosition();

        entityManager.persist(employees);
        entityManager.flush();
        Employees actualEmployee = repoEmployees.findAllWithPositionId(employees.getPosition().getId());

        assertEquals(employees,actualEmployee);

    }

    /**
     * Create Employee with Position
     */
    private Employees createEmployeeWithPosition() {
        Employees employees = createEmployee(false);
        Department department = new Department();
        department.setName("department 1");
        entityManager.persist(department);

        Position position = new Position();
        position.setDayForVacation(12);
        position.setDepartment(department);
        position.setName("position 1");
        position.setSalary(BigDecimal.valueOf(12323));
        entityManager.persist(position);

        employees.setPosition(position);
        return employees;
    }

    /**
     * Create Employee with User
     */
    public Employees createEmployee(boolean isUserNull) {
        Employees employees = new Employees();
        if (isUserNull) {
            employees.setUser(null);
        } else {
            Role role = new Role();
            role.setName("test role");
            entityManager.persist(role);

            User user = new User();
            user.setEmail("email@gmail.com");
            user.setPassword("ssssss");
            user.setRole(role);
            entityManager.persist(user);
            employees.setUser(user);
        }

        employees.setFirstName("first Name");
        employees.setLastName("Last name");
        employees.setAvailableVacationDay(10);
        employees.setExperience(23);
        employees.setStartWorkingDate(Calendar.getInstance().getTime());
        return employees;
    }


}

package net.restapp.repository;

import net.restapp.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepoWorkingHoursTest {
    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoWorkingHours repoWorkingHours;

    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        WorkingHours workingHours = createWorkingHours();
        entityManager.persist(workingHours);
        entityManager.persist(createWorkingHours());
        entityManager.flush();

        WorkingHours actual = repoWorkingHours.findOne(workingHours.getId());

        assertEquals(workingHours, actual);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {
        Long id = null;
        repoWorkingHours.findOne(id);
    }

    /**
     * Create WorkingHours
     */
    private WorkingHours createWorkingHours(){
        Status status = new Status();
        status.setName("status name");
        entityManager.persist(status);
        Event event = new Event();
        event.setName("event 1");
        entityManager.persist(event);

        WorkingHours workingHours = new WorkingHours();
        workingHours.setSalary(BigDecimal.valueOf(12.23));
        workingHours.setEmployees(createEmployee());
        workingHours.setEvent(event);
        workingHours.setHours(BigDecimal.valueOf(3));
        workingHours.setStartTime(Calendar.getInstance().getTime());
        workingHours.setStatus(status);

        return workingHours;
    }

    /**
     * Create Employee with User
     */
    private Employees createEmployee(){
        Role role = new Role();
        role.setName("test role");
        entityManager.persist(role);

        User user = new User();
        user.setEmail("email@gmail.com");
        user.setPassword("ssssss");
        user.setRole(role);
        entityManager.persist(user);

        Employees employees = new Employees();
        employees.setFirstName("first Name");
        employees.setLastName("Last name");
        employees.setAvailableVacationDay(10);
        employees.setExperience(23);
        employees.setStartWorkingDate(Calendar.getInstance().getTime());
        employees.setUser(user);
        entityManager.persist(employees);
        return employees;
    }
}

package net.restapp.repository;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.model.Role;
import net.restapp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepoArchiveSalaryTest {

    /**
     * Manager of alternative DB
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * The repository's layer object
     */
    @Autowired
    private RepoArchiveSalary repo;


    /**
     * The test-method for same-named repository's default method that get one entity by ID.
     */
    @Test
    public void findOne() {
        ArchiveSalary archiveSalary = createArchiveSalary(createEmployee(), Calendar.getInstance().getTime());
        ArchiveSalary archiveSalaryActual = repo.findOne(archiveSalary.getId());
        assertEquals(archiveSalary, archiveSalaryActual);
    }

    /**
     * The test-method for findOne repository's default method that get one entity by ID=null.
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void sendNullId() {

        Long id = null;
        repo.findOne(id);

    }

    /**
     * The test-method for same-name repository's method that get entity for employee and between dates
     */
    @Test
    public void findDateBetween() {
        Calendar calendar = Calendar.getInstance();
        List<ArchiveSalary> equalsList = new ArrayList<>();
        Employees employees = createEmployee();

        ArchiveSalary archiveSalary = createArchiveSalary(employees, calendar.getTime());
        entityManager.persist(archiveSalary);
        equalsList.add(archiveSalary);

        calendar.set(Calendar.DAY_OF_MONTH, -10);
        archiveSalary = createArchiveSalary(employees, calendar.getTime());
        entityManager.persist(archiveSalary);
        equalsList.add(archiveSalary);

        calendar.set(Calendar.DAY_OF_MONTH, -20);
        archiveSalary = createArchiveSalary(employees, calendar.getTime());
        entityManager.persist(archiveSalary);

        Employees employees1 = createEmployee();
        employees1.setFirstName("new name");
        calendar.set(Calendar.MINUTE, -20);
        archiveSalary = createArchiveSalary(employees1, calendar.getTime());
        entityManager.persist(archiveSalary);

        entityManager.flush();

        calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, -12);
        Date start = calendar.getTime();
        List<ArchiveSalary> findDateBetween = repo.findDateBetween(start, endDate, employees);

        assertEquals(findDateBetween, equalsList);

    }

    /**
     * The test-method for same-name repository's method that get entity by date end employee
     */
    @Test
    public void findSalaryViaDate() {
        Calendar calendar = Calendar.getInstance();
        Employees employees = createEmployee();

        ArchiveSalary archiveSalary = createArchiveSalary(employees, calendar.getTime());
        entityManager.persist(archiveSalary);

        employees = createEmployee();
        employees.setFirstName("new name");
        calendar.set(Calendar.MINUTE, -20);
        archiveSalary = createArchiveSalary(employees, calendar.getTime());
        entityManager.persist(archiveSalary);

        entityManager.flush();

        ArchiveSalary actualEmployee = repo.findSalaryViaDate(calendar.getTime(), employees);

        assertEquals(archiveSalary, actualEmployee);
    }

    /**
     * The test-method for same-named repository's method that get list entity by date.
     */
    @Test
    public void getAllByDate() {
        Calendar calendar = Calendar.getInstance();
        Employees employees = createEmployee();
        List<ArchiveSalary> list = new ArrayList<>();

        ArchiveSalary archiveSalary = createArchiveSalary(employees, calendar.getTime());
        list.add(archiveSalary);
        entityManager.persist(archiveSalary);

        employees = createEmployee();
        employees.setFirstName("new name");
        calendar.set(Calendar.MINUTE, -20);
        archiveSalary = createArchiveSalary(employees, calendar.getTime());
        list.add(archiveSalary);
        entityManager.persist(archiveSalary);

        entityManager.flush();

        List<ArchiveSalary> actualList = repo.getAllByDate(calendar.getTime());

        assertEquals(list, actualList);
    }

    /**
     * The test-method for same-named repository's method that get list entity by date.
     */
    @Test
    public void getAllByDateNotEqual() {
        Calendar calendar = Calendar.getInstance();
        Employees employees = createEmployee();
        entityManager.persist(employees);
        List<ArchiveSalary> list = new ArrayList<>();

        ArchiveSalary archiveSalary = createArchiveSalary(employees, calendar.getTime());
        list.add(archiveSalary);
        entityManager.persist(archiveSalary);

        employees = createEmployee();
        entityManager.persist(employees);
        employees.setFirstName("new name");
        calendar.set(Calendar.DAY_OF_MONTH, -10);
        archiveSalary = createArchiveSalary(employees, calendar.getTime());
        list.add(archiveSalary);
        entityManager.persist(archiveSalary);

        entityManager.flush();

        List<ArchiveSalary> actualList = repo.getAllByDate(calendar.getTime());

        assertNotEquals(list, actualList);
    }

    /**
     * Create ArchiveSalary with Employee and date
     */
    private ArchiveSalary createArchiveSalary(Employees employees, Date date) {
        ArchiveSalary archiveSalary = new ArchiveSalary();
        archiveSalary.setEmployee(employees);
        archiveSalary.setMonthSalary(BigDecimal.valueOf(233.4));
        archiveSalary.setDate(date);
        entityManager.persist(archiveSalary);
        entityManager.flush();
        return archiveSalary;
    }

    /**
     * Create Employee with User
     */
    private Employees createEmployee() {

        Role role = new Role();
        role.setName("test role");
        entityManager.persist(role);

        User user = new User();
        user.setEmail("email@gmail.com");
        user.setPassword("ssssss");
        user.setRole(role);
        entityManager.persist(user);

        Employees employees = new Employees();
        employees.setUser(user);
        employees.setFirstName("first Name");
        employees.setLastName("Last name");
        employees.setAvailableVacationDay(10);
        employees.setExperience(23);
        employees.setStartWorkingDate(Calendar.getInstance().getTime());
        entityManager.persist(employees);
        return employees;
    }
}

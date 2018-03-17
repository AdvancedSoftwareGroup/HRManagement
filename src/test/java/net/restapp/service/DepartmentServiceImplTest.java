package net.restapp.service;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Department;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.repository.RepoDepartment;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.IService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceImplTest {

    /**
     * The service's layer object
     */
    @Autowired
    private IService<Department> departmentService;

    /**
     * The repository's layer object
     */
    @MockBean
    private RepoDepartment repoDepartment;

    /**
     * The service's layer object
     */
    @MockBean
    private EmployeesService employeesService;

    /**
     * Init data for testing
     */
    @Before
    public void setUp() {

        Department department = new Department();
        department.setName("Department 1");
        Department department2 = new Department();
        department2.setName("Department 2");
        List<Department> list = Arrays.asList(department, department2);

        Position position = new Position();
        position.setName("position 1");
        department.setPositions(Arrays.asList(position));
        Employees employees = new Employees();
        employees.setFirstName("Employee");

        Mockito.when(repoDepartment.findAll())
                .thenReturn(list);

        Mockito.when(repoDepartment.findOne(1L))
                .thenReturn(department);

        Mockito.when(repoDepartment.findOne(2L))
                .thenReturn(department2);

        Mockito.when(employeesService.getWithPositionId(1L)).
                 thenReturn(employees);

        Mockito.when(employeesService.getWithPositionId(2L)).
                thenReturn(null);


    }

    /**
     * The test-method for same-named service's
     * (Try delete department where works employees)
     * @throws EntityConstraintException
     */
    @Test(expected = EntityConstraintException.class)
    public void deleteAndSendException() {
        // Arrange
        doNothing().when(repoDepartment).delete(1L);
        // Act
        departmentService.delete(1L);
        // Assert
        verify(repoDepartment, times(1)).delete(1L);
    }

    /**
     * The test-method for delete method from service's
     * (Try delete department without working employees)
     */
    @Test
    public void delete(){
        // Arrange
        doNothing().when(repoDepartment).delete(2L);
        // Act
        departmentService.delete(2L);
        // Assert
        verify(repoDepartment, times(1)).delete(2L);
    }

    /**
     * The test-method for same-named service's default method that get all entity.
     */
    @Test
    public void getAll() {
        Department department = new Department();
        department.setName("Department 1");
        Department department2 = new Department();
        department2.setName("Department 2");
        List<Department> list = Arrays.asList(department, department2);

        List<Department> found = departmentService.getAll();

        assertEquals(found, list);
    }

    /**
     * The test-method for same-named service's default method that get one entity by Id.
     */
    @Test
    public void getOne() {
        Department department = new Department();
        department.setName("Department 1");

        Department found = departmentService.getById(1L);

        assertEquals(found, department);
    }


}

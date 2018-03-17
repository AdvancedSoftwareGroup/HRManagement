package net.restapp.service;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Department;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.repository.RepoPosition;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.IService;
import net.restapp.servise.PositionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionServiceImplTest {

    /**
     * The service's layer object
     */
    @Autowired
    private PositionService positionService;
    /**
     * The service's layer object
     */
    @MockBean
    private EmployeesService employeesService;

    /**
     * The service's layer object
     */
    @MockBean
    private IService<Department> departmentService;

    /**
     * The repository's layer object
     */
    @MockBean
    private RepoPosition repoPosition;


    /**
     * Init data for testing
     */
    @Before
    public void setUp() {
        Position position = new Position();
        position.setName("position 1");
        Position position2 = new Position();
        position2.setName("position 2");
        List<Position> list = Arrays.asList(position, position2);

        Mockito.when(repoPosition.findAll())
                .thenReturn(list);

        Mockito.when(repoPosition.findOne(1L))
                .thenReturn(position);

        Mockito.when(employeesService.getWithPositionId(1L)).
                thenReturn(new Employees());

        Mockito.when(employeesService.getWithPositionId(2L)).
                thenReturn(null);

        Mockito.when(departmentService.getById(1L)).
                thenReturn(null);

        Mockito.when(departmentService.getById(2L)).
                thenReturn(new Department());

    }

    /**
     * The test-method for save method from service's
     * Expect exception because department for position don't exist
     */
    @Test(expected = EntityNotFoundException.class)
    public void saveWithoutExistingDepartment(){
        Position position = new Position();
        Department department = new Department();
        department.setId(1L);
        position.setDepartment(department);

        // Arrange
        when(repoPosition.save(position)).thenReturn(position);
        // Act
        positionService.save(position);
        // Assert
        verify(repoPosition, times(1)).save(position);
    }

    /**
     * The test-method for save method from service's
     */
    @Test
    public void save() {
        Position position = new Position();
        Department department = new Department();
        department.setId(2L);
        position.setDepartment(department);

        // Arrange
        when(repoPosition.save(position)).thenReturn(position);
        // Act
        positionService.save(position);
        // Assert
        verify(repoPosition, times(1)).save(position);
    }

    /**
     * The test-method for delete method from service's
     * (Try delete position without working employees)
     */
    @Test
    public void delete(){
        // Arrange
        doNothing().when(repoPosition).delete(2L);
        // Act
        positionService.delete(2L);
        // Assert
        verify(repoPosition, times(1)).delete(2L);
    }

    /**
     * The test-method for delete method from service's
     * (Try delete position without working employees)
     */
    @Test(expected = EntityConstraintException.class)
    public void deletePositionThatHaveEmployee() {
        // Arrange
        doNothing().when(repoPosition).delete(1L);
        // Act
        positionService.delete(1L);
        // Assert
        verify(repoPosition, times(1)).delete(1L);
    }

    /**
     * The test-method for same-named service's default method that get all entity.
     */
    @Test
    public void getAll() {
        Position position = new Position();
        position.setName("position 1");
        Position position2 = new Position();
        position2.setName("position 2");
        List<Position> list = Arrays.asList(position, position2);
        List<Position> found = positionService.getAll();

        assertEquals(found, list);
    }

    /**
     * The test-method for same-named service's default method that get one entity by Id.
     */
    @Test
    public void getOne() {
        Position position = new Position();
        position.setName("position 1");
        Position found = positionService.getById(1L);

        assertEquals(found, position);
    }

    /**
     * The test-method for same-named service's method
     * that return true if position don't have eny employee.
     */
    @Test
    public void isPositionFree(){
        assertEquals(positionService.isPositionFree(1L),false);
        assertEquals(positionService.isPositionFree(2L),true);
    }
}

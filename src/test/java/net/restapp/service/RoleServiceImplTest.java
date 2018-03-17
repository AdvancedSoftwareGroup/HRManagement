package net.restapp.service;

import net.restapp.model.Role;
import net.restapp.repository.RepoRole;
import net.restapp.servise.IService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

    /**
     * The service's layer object
     */
    @Autowired
    private IService<Role> roleService;

    /**
     * The repository's layer object
     */
    @MockBean
    private RepoRole repoRole;

    /**
     * Init data for testing
     */
    @Before
    public void setUp() {

        Role role = new Role();
        role.setName("role 1");
        Role role2 = new Role();
        role2.setName("role 2");
        List<Role> list = Arrays.asList(role, role2);

        when(repoRole.findAll())
                .thenReturn(list);
        when(repoRole.findOne(1L))
                .thenReturn(role);

    }

    /**
     * The test-method for same-named service's default method that save entity.
     * Try save entity with null id.
     */
    @Test
    public void saveNull(){
         trySave(null);
    }
    /**
     * The test-method for same-named service's default method that save entity.
     */
    @Test
    public void save()  {
        Role role = new Role();
        role.setId(5);
        trySave(role);
    }

    /**
     * The test-method for same-named service's default method that save entity.
     * Try save entity with default id.
     */
    @Test(expected = IllegalArgumentException.class)
    public void saveDefaultRole()  {
        Role role = new Role();
        role.setId(1);
        trySave(role);
    }

    /**
     * The test-method for same-named service's default method that delete entity by id.
     * try delete unchanged role
     */
    @Test(expected = IllegalArgumentException.class)
    public void deleteDefaultRole() {
        tryDelete(3L);
    }

    /**
     * The test-method for same-named service's default method that delete entity by id.
     * try delete changed role
     */
    @Test
    public void delete() {
       tryDelete(4L);
    }

    /**
     * The test-method for same-named service's default method that get all entity.
     */
    @Test
    public void getAll() {
        Role role = new Role();
        role.setName("role 1");
        Role role2 = new Role();
        role2.setName("role 2");
        List<Role> list = Arrays.asList(role, role2);

        List<Role> found = roleService.getAll();

        assertEquals(found, list);
    }

    /**
     * The test-method for same-named service's default method that get one entity by Id.
     */
    @Test
    public void getOne() {
        Role role = new Role();
        role.setName("role 1");

        Role found = roleService.getById(1L);

        assertEquals(found, role);
    }

    /**
     * The test-method for delete method from service's by id
     * @param id - role's id
     */
    private void tryDelete(Long id) {
        // Arrange
        doNothing().when(repoRole).delete(id);
        // Act
        roleService.delete(id);
        // Assert
        verify(repoRole, times(1)).delete(id);
    }

    /**
     * The test-method for save method from service's
     * @param role - role
     */
    private void trySave(Role role) {
        // Arrange
        when(repoRole.save(role)).thenReturn(role);
        // Act
        roleService.save(role);
        // Assert
        verify(repoRole, times(1)).save(role);
    }
}

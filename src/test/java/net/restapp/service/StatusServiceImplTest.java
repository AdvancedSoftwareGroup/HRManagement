package net.restapp.service;

import net.restapp.model.Status;
import net.restapp.repository.RepoStatus;
import net.restapp.servise.IService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusServiceImplTest {

    /**
     * The service's layer object
     */
    @Autowired
    private IService<Status> statusService;

    /**
     * The repository's layer object
     */
    @MockBean
    private RepoStatus repoStatus;

    /**
     * The test-method for same-named service's default method that save entity.
     * Try save null entity
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
        Status status = new Status();
        status.setId(0);
        trySave(status);
    }

    /**
     * The test-method for same-named service's default method that save entity.
     * try save unchanged entity
     */
    @Test(expected = IllegalArgumentException.class)
    public void saveDefaultStatus()  {
        Status status = new Status();
        status.setId(5);
        // Arrange
        trySave(status);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete unchanged entity
     */
    @Test(expected = IllegalArgumentException.class)
    public void deleteDefaultStatus() {
        tryDelete(3L);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete entity
     */
    @Test
    public void delete() {
        tryDelete(6L);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete entity with id = null
     */
    @Test
    public void deleteNullID() {
        tryDelete(null);
    }

    /**
     * The test-method for same-named service's default method that delete entity by Id.
     * try delete entity
     * @param id - status's id
     */
    private void tryDelete(Long id) {
        // Arrange
        doNothing().when(repoStatus).delete(id);
        // Act
        statusService.delete(id);
        // Assert
        verify(repoStatus, times(1)).delete(id);
    }

    /**
     * The test-method for same-named service's default method that save entity.
     * try save entity
     * @param status  - status
     */
    private void trySave(Status status) {
        // Arrange
        when(repoStatus.save(status)).thenReturn(status);
        // Act
        statusService.save(status);
        // Assert
        verify(repoStatus, times(1)).save(status);
    }

}

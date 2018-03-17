package net.restapp.service;

import net.restapp.model.Event;
import net.restapp.repository.RepoEvent;
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
public class EventServiceImplTest {
    /**
     * The service's layer object
     */
    @Autowired
    private IService<Event> eventService;

    /**
     * The repository's layer object
     */
    @MockBean
    private RepoEvent repoEvent;

    /**
     * Init data for testing
     */
    @Before
    public void setUp() {

        Event event = new Event();
        event.setName("Event 1");
        Event event2 = new Event();
        event2.setName("Event 1");
        List<Event> list = Arrays.asList(event, event2);

        when(repoEvent.findAll())
                .thenReturn(list);
        when(repoEvent.findOne(1L))
                .thenReturn(event);

    }


    /**
     * The test-method for same-named service's default method that save null entity.
     */
    @Test
    public void saveNull() {
        Event event = null;
        // Arrange
        when(repoEvent.save(event)).thenReturn(event);
        // Act
        eventService.save(event);
        // Assert
        verify(repoEvent, times(1)).save(event);
    }


    /**
     * The test-method for same-named service's default method that save entity.
     */
    @Test
    public void save() {
        Event event = new Event();
        event.setName("Event 1");
        // Arrange
        when(repoEvent.save(event)).thenReturn(event);
        // Act
        eventService.save(event);
        // Assert
        verify(repoEvent, times(1)).save(event);

    }

    /**
     * The test-method for delete method from service's
     */
    @Test
    public void delete() {
        // Arrange
        doNothing().when(repoEvent).delete(1L);
        // Act
        eventService.delete(1L);
        // Assert
        verify(repoEvent, times(1)).delete(1L);
    }

    /**
     * The test-method for same-named service's default method that get all entity.
     */
    @Test
    public void getAll() {
        Event event = new Event();
        event.setName("Event 1");
        Event event2 = new Event();
        event2.setName("Event 1");
        List<Event> list = Arrays.asList(event, event2);

        List<Event> found = eventService.getAll();

        assertEquals(found, list);
    }

    /**
     * The test-method for same-named service's default method that get one entity by Id.
     */
    @Test
    public void getOne() {
        Event event = new Event();
        event.setName("Event 1");

        Event found = eventService.getById(1L);

        assertEquals(found, event);
    }



}

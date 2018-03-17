package net.restapp.servise.impl;

import net.restapp.model.Event;
import net.restapp.repository.RepoEvent;
import net.restapp.servise.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service's layer of application for Event.
 */
@Service
public class EventServiceImpl  implements IService<Event> {

    /**
     * The field of Event repository's layer that is called for use it's methods
     */
    @Autowired
    RepoEvent repoEvent;

    /**
     * The method calls a repository's method for save an event
     */
    @Override
    public void save(Event event) {
        repoEvent.save(event);
    }

    /**
     * The method calls a repository's method for delete an event by Id
     */
    @Override
    public void delete(Long id) {
        repoEvent.delete(id);
    }

    /**
     * The method calls a repository's method for get all events
     */
    @Override
    public List<Event> getAll() {
        return repoEvent.findAll();
    }

    /**
     * The method calls a repository's method for gat an event by Id
     */
    @Override
    public Event getById(Long id) {
        return repoEvent.findOne(id);
    }
}

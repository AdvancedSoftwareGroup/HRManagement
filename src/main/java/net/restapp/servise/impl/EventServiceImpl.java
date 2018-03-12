package net.restapp.servise.impl;

import net.restapp.model.Event;
import net.restapp.repository.RepoEvent;
import net.restapp.servise.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl  implements EventService {

    @Autowired
    RepoEvent repoEvent;

    @Override
    public void save(Event event) {
        repoEvent.save(event);
    }

    @Override
    public void delete(Long id) {
        repoEvent.delete(id);
    }

    @Override
    public List<Event> getAll() {
        return repoEvent.findAll();
    }

    @Override
    public Event getById(Long id) {
        return repoEvent.findOne(id);
    }
}

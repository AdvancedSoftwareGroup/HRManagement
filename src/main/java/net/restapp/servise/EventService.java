package net.restapp.servise;

import net.restapp.model.Event;

import java.util.List;

public interface EventService {

    void save(Event event);

    void delete(Long id);

    List<Event> getAll();

    Event getById(Long id);
}

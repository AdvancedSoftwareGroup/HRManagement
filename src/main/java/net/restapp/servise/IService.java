package net.restapp.servise;

import java.util.List;

/**
 * Interface for service's layer of application (CRUD methods)
 */

public interface IService<T> {

    void save(T entity);

    void delete(Long id);

    T getById(Long id);

    List<T> getAll();
}

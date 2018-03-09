package net.restapp.servise;

import net.restapp.model.Status;

import java.util.List;

public interface StatusService {

    void save(Status status) throws Exception;
    void delete(Long id) throws Exception;
    List<Status> getAll();
    Status getById(Long id);
    Status findByName(String name);
    boolean isStatusExist(Status status);
}

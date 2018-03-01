package net.restapp.servise;

import net.restapp.model.Department;
import net.restapp.model.Status;

import java.util.List;

public interface StatusService {

    void save(Status status);
    void delete(Long id);
    List<Status> getAll();
    Status getById(Long id);
    Status findByName(String name);
    boolean isStatusExist(Status status);
}

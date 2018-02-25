package net.restapp.servise;


import net.restapp.model.Department;

import java.util.List;

public interface DepartmentService {

    void save(Department department);

    void delete(Long id);

    List<Department> getAll();

    Department getById(Long id);

}

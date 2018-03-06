package net.restapp.servise;

import net.restapp.model.Employees;

import java.util.List;

public interface EmployeesService {

    void save(Employees employees);

    void delete(Long id);

    List<Employees> getAll();

    Employees getById(Long id);


}

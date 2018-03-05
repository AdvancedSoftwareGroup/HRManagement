package net.restapp.servise;

import net.restapp.model.Employees;

import java.util.List;

public interface EmployeesService {

    void add(Employees employees);

    void edit(Employees employees);

    void delete(Long id);

    List<Employees> getAll();

    Employees getById(Long id);


}

package net.restapp.servise;

import net.restapp.dto.EmployeeChangeRoleDTO;
import net.restapp.model.Employees;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface EmployeesService {

    void save(Employees employees);

    void delete(Long id);

    List<Employees> getAll();

    Employees getById(Long id);

    List<Employees> getAllByRoleId(Long id);

    void updateEmployeeRole(EmployeeChangeRoleDTO dto) throws AccessDeniedException;

    boolean isEmployeeExist(Long employeeId);

    Employees getWithPositionId(Long id);
}

package net.restapp.servise;

import net.restapp.dto.EmployeeChangeRoleDTO;
import net.restapp.model.Employees;

import java.nio.file.AccessDeniedException;
import java.util.List;
/**
 * Interface for service's layer of Employees. Extends CRUD methods from {@link net.restapp.servise.IService}
 */

public interface EmployeesService extends IService<Employees> {

    /**
     * get all employee with role
     * @param id - role's id
     * @return - list employees
     */
    List<Employees> getAllByRoleId(Long id);

    /**
     * update employee role
     * @param dto
     * @throws AccessDeniedException
     */
    void updateEmployeeRole(EmployeeChangeRoleDTO dto) throws AccessDeniedException;

    /**
     * check is employee already exist
     * @param employeeId - employee's ID
     * @return - true if employee already exist and false in another case
     */
    boolean isEmployeeExist(Long employeeId);

    /**
     * get employee with position
     * @param id - position's ID
     * @return - employee
     */
    Employees getWithPositionId(Long id);

    /**
     * Get available vacation days by employee's ID
     * @param id - employee's ID
     * @return - available vacation days
     */
    Integer getAvailableVacationDay(Long id);
}

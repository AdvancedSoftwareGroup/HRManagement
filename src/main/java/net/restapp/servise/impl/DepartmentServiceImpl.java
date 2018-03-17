package net.restapp.servise.impl;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Department;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.repository.RepoDepartment;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The service's layer of application for Department, contains CRUD methods
 */

@Service
public class DepartmentServiceImpl implements IService<Department> {

    /**
     * The field of Department repository's layer that is called for use it's methods
     */
    @Autowired
    RepoDepartment repoDepartment;

    /**
     * The field of Employee service's layer that is called for use it's methods
     */
    @Autowired
    EmployeesService employeesService;

    /**
     * The method calls a repository's method for save a department
     * @param department - department
     */
    @Override
    public void save(Department department) {
        repoDepartment.save(department);
    }

    /**
     * The method calls repository's method for delete a department by ID
     * @param id - ID of the department for delete
     */
    @Override
    public void delete(Long id) {
        List<Employees> employees = getListEmployeeForDepartmentId(id);
        if (!employees.isEmpty())
            throw new EntityConstraintException("You can't delete department until some employee work there");
        repoDepartment.delete(id);
    }

    /**
     * Return list of Employees witch work at the department
     * @param id - department id
     * @return - list of employees
     */
    private List<Employees> getListEmployeeForDepartmentId(Long id) {
        Department department = repoDepartment.findOne(id);
        List<Position> positions = department.getPositions();
        List<Employees> employeesList = new ArrayList<>();
        if (positions != null) {
            for (Position position : positions) {
                employeesList.add(employeesService.getWithPositionId(position.getId()));
            }
        }
        return employeesList;
    }

    /**
     * The method calls repository's method for find all departments
     */
    @Override
    public List<Department> getAll() {
        return repoDepartment.findAll();
    }

    /**
     * The method calls repository's method for find a department by ID
     * @param id - department's id
     */
    @Override
    public Department getById(Long id) {
        return repoDepartment.findOne(id);
    }


}

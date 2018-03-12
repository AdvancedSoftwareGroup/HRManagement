package net.restapp.servise.impl;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Department;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.repository.RepoDepartment;
import net.restapp.servise.DepartmentService;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    RepoDepartment repoDepartment;

    @Autowired
    EmployeesService employeesService;

    @Override
    public void save(Department department) {
        repoDepartment.save(department);
    }

    @Override
    public void delete(Long id) {
        List<Employees> employees = getListEmployeeForDepartmentId(id);
        if (!employees.isEmpty())
            throw new EntityConstraintException("You can't delete department until some employee work there");
        repoDepartment.delete(id);
    }

    private List<Employees> getListEmployeeForDepartmentId(Long id) {
        Department department = repoDepartment.findOne(id);
        List<Position> positions = department.getPositions();
        List<Employees> employeesList = new ArrayList<>();
        for (Position position: positions) {
            employeesList.add(employeesService.getWithPositionId(position.getId()));
        }
        return employeesList;
    }

    @Override
    public List<Department> getAll() {
        return repoDepartment.findAll();
    }

    @Override
    public Department getById(Long id) {
        return repoDepartment.findOne(id);
    }



}

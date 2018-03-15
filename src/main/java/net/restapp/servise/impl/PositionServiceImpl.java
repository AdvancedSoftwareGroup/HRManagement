package net.restapp.servise.impl;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Department;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.repository.RepoPosition;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.IService;
import net.restapp.servise.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
/**
 * The service's layer of application for Position, implements {@link net.restapp.servise.PositionService}
 */
@Service
public class PositionServiceImpl implements PositionService {

    /**
     * The field of Position repository's layer that is called for use it's methods
     */
    @Autowired
    RepoPosition repoPosition;

    /**
     * The field of Department service's layer that is called for use it's methods
     */
    @Autowired
    IService<Department> departmentService;

    /**
     * The field of Employee service's layer that is called for use it's methods
     */
    @Autowired
    EmployeesService employeesService;

    /**
     * The method calls a repository's method for save a position
     * if department of this position exist at database
     * @param position
     */
    @Override
    public void save(Position position) {
        Long departmentId = position.getDepartment().getId();
        Department department = departmentService.getById(departmentId);

        if (department == null) {
            throw new EntityNotFoundException(
                    "department with id="+departmentId +" don't present in database");
        }

        repoPosition.save(position);
    }

    /**
     * The method calls a repository's method for delete a position by ID
     * if position is free
     */
    @Override
    public void delete(Long id) {
        if (!isPositionFree(id))
            throw new EntityConstraintException("cant delete position becouse some employee still work on it");
        repoPosition.delete(id);
    }

    /**
     * The method calls a repository's method for getAll a position
     */
    @Override
    public List<Position> getAll() {
        return repoPosition.findAll();
    }

    /**
     * The method calls a repository's method for get a position by ID
     */
    @Override
    public Position getById(Long id) {
        return repoPosition.findOne(id);
    }

    /**
     * Check are positions have employee
     * @param id - position id
     * @return - if positions don't have employee return true. In ather case return false
     */
    public boolean isPositionFree(Long id) {
        Employees employees = employeesService.getWithPositionId(id);
        return (employees == null);
    }
}

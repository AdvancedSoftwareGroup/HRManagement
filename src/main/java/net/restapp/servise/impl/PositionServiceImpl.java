package net.restapp.servise.impl;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Department;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.repository.RepoDepartment;
import net.restapp.repository.RepoPosition;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    RepoPosition repoPosition;

    @Autowired
    RepoDepartment repoDepartment;

    @Autowired
    EmployeesService employeesService;

    @Override
    public void save(Position position) {
        Long departmentId = position.getDepartment().getId();
        Department department = repoDepartment.findOne(departmentId);

        if (department == null) {
            throw new EntityNotFoundException(
                    "department with id="+departmentId +" don't present in database");
        }

        repoPosition.save(position);
    }

    @Override
    public void delete(Long id) {
        if (!isPositionFree(id))
            throw new EntityConstraintException("cant delete position becouse some employee still work on it");
        repoPosition.delete(id);
    }

    @Override
    public List<Position> getAll() {
        return repoPosition.findAll();
    }

    @Override
    public Position getById(Long id) {
        return repoPosition.findOne(id);
    }

    private boolean isPositionFree(Long id) {
        Employees employees = employeesService.getWithPositionId(id);
        return (employees == null);
    }
}

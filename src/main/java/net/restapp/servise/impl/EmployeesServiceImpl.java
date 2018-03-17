package net.restapp.servise.impl;

import net.restapp.dto.EmployeeChangeRoleDTO;
import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.model.User;
import net.restapp.repository.RepoEmployees;
import net.restapp.repository.RepoPosition;
import net.restapp.repository.RepoUser;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * The service's layer of application for Employee, contains CRUD methods
 */
@Service
public class EmployeesServiceImpl implements EmployeesService {

    /**
     * The field of Employee repository's layer that is called for use it's methods
     */
    @Autowired
    RepoEmployees repoEmployees;

    /**
     * The field of User repository's layer that is called for use it's methods
     */
    @Autowired
    RepoUser repoUser;

    /**
     * The field of User service layer that is called for use it's methods
     */
    @Autowired
    UserService userService;

    /**
     * The field of Position repository's layer that is called for use it's methods
     */
    @Autowired
    RepoPosition repoPosition;

    /**
     * Validate employee and save it.
     *
     * @param employees
     */
    @Override
    @Transactional
    public void save(Employees employees) {
        if (employees == null) throw new IllegalArgumentException("you try save employee with equals null");
        Long positionId = getPositionIExist(employees);

        if (employees.getId() == 0) {
            isPositionFree(employees, positionId);
            addNewUser(employees);
        }
        employees.setUser(getUserByEmployeeId(employees.getId()));

        repoEmployees.save(employees);
    }

    /**
     * set default values for employee and create User for it
     *
     * @param employees - employee
     */
    private void addNewUser(Employees employees) {
        //vacation day for first half a year equals 0;
        employees.setAvailableVacationDay(0);
        userService.save(employees.getUser());
    }

    /**
     * Check is position for employee free
     *
     * @param employees  - employee
     * @param positionId - position ID
     *                   if not throw  EntityAlreadyExistException
     */
    private void isPositionFree(Employees employees, Long positionId) {
        Employees databaseEmployee = repoEmployees.findAllWithPositionId(positionId);
        if (databaseEmployee != null) {
            if (databaseEmployee.getPosition().getId() == employees.getPosition().getId()) {
                throw new EntityAlreadyExistException(
                        "Employee for position with positionid=" + positionId + " already exist.");
            }
        }
    }

    /**
     * Check is position exist
     *
     * @param employees - employee
     * @return - position ID if exist and trow EntityNotFoundException if not.
     */
    @NotNull
    private Long getPositionIExist(Employees employees) {
        Long positionId = employees.getPosition().getId();
        Position position = repoPosition.findOne(positionId);
        if (position == null) {
            throw new EntityNotFoundException(
                    "position with id=" + positionId + " don't exist at database. " +
                            "Please, create it or select another one.");
        }
        return positionId;
    }

    /**
     * The method calls repository's method for delete a employee by ID
     *
     * @param id - ID of the employee for delete
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("you try delete employee with id=null");
        Employees employees = getById(id);
        if (employees == null) {
            String msg = String.format("There is no employee with id: %d", id);
            throw new EntityNotFoundException(msg);
        }
        repoEmployees.delete(id);
        repoUser.delete(employees.getUser().getId());
    }

    /**
     * The method calls repository's method for find all employees
     */
    @Override
    public List<Employees> getAll() {
        return repoEmployees.findAll();
    }

    /**
     * The method calls repository's method for get one employee by ID
     */
    @Override
    public Employees getById(Long id) {
        return repoEmployees.findOne(id);
    }

    /**
     * Find employees with role
     * @param id - role's id
     * @return - list of employees
     */
    @Override
    public List<Employees> getAllByRoleId(Long id) {
        List<Employees> employeesList = new ArrayList<>();
        List<User> userList = userService.getAllByRoleId(id);
        for (User user : userList) {
            if (user.getId() != 1) employeesList.add(user.getEmployees());
        }
        return employeesList;
    }

    /**
     * The method change find employee and calls a userService
     * to change role for user
     */
    @Override
    @Transactional
    public void updateEmployeeRole(EmployeeChangeRoleDTO dto) throws AccessDeniedException {
        Employees employees = repoEmployees.findOne(dto.getEmployeeId());
        userService.updateUserRole(employees.getUser(), dto.getRoleId());
    }

    /**
     * check is employee with id exist at database
     */
    @Override
    public boolean isEmployeeExist(Long id) {
        Employees employees = repoEmployees.getOne(id);
        return employees != null;
    }

    /**
     * Get employee with position ID
     * @param id - position's ID
     * @return - employee
     */
    @Override
    public Employees getWithPositionId(Long id) {
        return repoEmployees.findAllWithPositionId(id);
    }

    /**
     * Get available vacation days for employee
     * @param id - employee's ID
     * @return available vacation days
     */
    @Override
    public Integer getAvailableVacationDay(Long id) {
        return repoEmployees.findAvailableVacationDay(id);
    }

    /**
     * Get user that ecvivalent employee
     * @param id - employee's ID
     * @return - user
     */
    public User getUserByEmployeeId(Long id) {
        Employees employees = repoEmployees.getOne(id);
        return repoUser.getOne(employees.getUser().getId());
    }
}

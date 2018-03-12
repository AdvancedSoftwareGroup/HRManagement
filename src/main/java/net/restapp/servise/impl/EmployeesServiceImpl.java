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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService {


    @Autowired
    RepoEmployees repoEmployees;

    @Autowired
    RepoUser repoUser;

    @Autowired
    UserService userService;

    @Autowired
    RepoPosition repoPosition;

    @Override
    @Transactional
    public void save(Employees employees) {
        Long positionId = employees.getPosition().getId();
        Position position = repoPosition.findOne(positionId);
        if (position == null) {
            throw new EntityNotFoundException(
                    "position with id=" + positionId + " don't exist at database. " +
                            "Please, create it or select another one.");
        }


        if (employees.getId() == 0) {
            //vacation day for first half a year equals 0;
            employees.setAvailableVacationDay(0);
            Employees databaseEmployee = repoEmployees.findAllWithPositionId(positionId);
            if (databaseEmployee != null) {
                if (databaseEmployee.getPosition().getId() == employees.getPosition().getId()) {
                    throw new EntityAlreadyExistException(
                            "Employee for position with positionid=" + positionId + " already exist.");
                }
            }
            userService.save(employees.getUser());
        }

        employees.setUser(getUserByEmployeeId(employees.getId()));
        repoEmployees.save(employees);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employees employees = getById(id);
        if (employees == null) {
            String msg = String.format("There is no employee with id: %d", id);
            throw new EntityNotFoundException(msg);
        }
        repoEmployees.delete(id);
        repoUser.delete(employees.getUser().getId());
    }

    @Override
    public List<Employees> getAll() {
        return repoEmployees.findAll();
    }

    @Override
    public Employees getById(Long id) {
        return repoEmployees.findOne(id);
    }



    @Override
    public List<Employees> getAllByRoleId(Long id) {
        List<Employees> employeesList = new ArrayList<>();
        List<User> userList = userService.getAllByRoleId(id);
        for (User user: userList) {
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

    @Override
    public Employees getWithPositionId(Long id) {
        return repoEmployees.findAllWithPositionId(id);
    }


    public User getUserByEmployeeId(Long id) {
        Employees employees = repoEmployees.getOne(id);
        return repoUser.getOne(employees.getUser().getId());
    }
}

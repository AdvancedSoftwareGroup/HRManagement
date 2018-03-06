package net.restapp.servise;

import net.restapp.exception.EntityAlreadyExistException;
import net.restapp.model.Employees;
import net.restapp.model.Position;
import net.restapp.model.User;
import net.restapp.repository.RepoEmployees;
import net.restapp.repository.RepoPosition;
import net.restapp.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
    public void save (Employees employees){
        Long positionId = employees.getPosition().getId();

        if (employees.getId() == 0){
            employees.setAvailableVacationDay(0);

            Employees databaseEmployee = repoEmployees.findAllWithPositionId(positionId);
            if (databaseEmployee != null) {
                if (databaseEmployee.getPosition().getId() != employees.getPosition().getId()) {
                    throw new EntityAlreadyExistException(
                            "Employee for position with positionid=" + positionId + " already exist.");
                }
            }
        }

        Position position = repoPosition.findOne(positionId);
        if (position == null){
            throw new EntityNotFoundException(
                    "position with id="+positionId+" don't exist at database. " +
                            "Please, create it or select another one.");
        }
        userService.save(employees.getUser());
        User user = userService.findByEmail(employees.getUser().getEmail());
        employees.setUser(user);
        repoEmployees.save(employees);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Employees employees = getById(id);
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



}

package net.restapp.servise.impl;

import net.restapp.exception.EmployeeNotAbleToWorkException;
import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.repository.RepoWorkingHours;
import net.restapp.servise.CountService;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * The service's layer of application for Department, contains CRUD methods
 */

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    /**
     * The field of WorkingHours repository's layer that is called for use it's methods
     */
    @Autowired
    RepoWorkingHours repoWorkingHours;

    /**
     * The field of Employee service's layer that is called for use it's methods
     */
    @Autowired
    EmployeesService employeesService;

    /**
     * The field of Count service that is called for use it's methods
     */
    @Autowired
    CountService countService;

    private final static BigDecimal MAX_WORK_HOUR_FOR_DAY = BigDecimal.valueOf(16);

    /**
     * The method calls a repository's method for save a workingHours
     * @param workingHours - workingHours
     */
    @Transactional
    @Override
    public void save(WorkingHours workingHours) {
        isWeCanAddEvent(workingHours);
        isEmployeeFree(workingHours);
        BigDecimal salary = countService.calculatePaymentOfEvent(workingHours);
        workingHours.setSalary(salary);
        repoWorkingHours.save(workingHours);
    }

    /**
     * Check is employee free and have enough time for event
     * @param workingHours - workingHours
     */
    private void isEmployeeFree(WorkingHours workingHours) {
        BigDecimal sumHours = repoWorkingHours.getSumWorkingHourForEmployeeAndDate(
                workingHours.getStartTime(),
                workingHours.getEmployees());
        if (sumHours == null) return;

        BigDecimal newSum = sumHours.multiply(workingHours.getHours());

        if (newSum.compareTo(MAX_WORK_HOUR_FOR_DAY) >= 0 )
            throw new EmployeeNotAbleToWorkException("You can't add event because" +
                    " employee will be work more than 16 hours. " +
                    "Now employee already works "+sumHours +" hours");

    }

    /**
     * Check equivalents of status ID and events ID
     * @param workingHours - workingHours
     */
    private void isWeCanAddEvent(WorkingHours workingHours) {
        if (workingHours.getStatus().getId() > 1 && workingHours.getStatus().getId() < 6){
            if (workingHours.getEvent().getId() != 1) {
                throw new EntityConstraintException("for 1 < statusId < 6 event id must be equal 1");
            }
        }
    }

    /**
     * The method calls repository's method for delete a workingHours by ID
     * @param id - ID of the workingHours for delete
     */
    @Override
    public void delete(Long id) {
        repoWorkingHours.delete(id);
    }

    /**
     * The method calls repository's method for get all workingHours
     * @return  list workingHOurs
     */
    @Override
    public List<WorkingHours> getAll() {
        return repoWorkingHours.findAll();
    }

    /**
     * The method calls repository's method for get one workingHours by ID
     * @param id - workingHours's ID
     * @return - workingHours
     */
    @Override
    public WorkingHours getById(Long id) {
        return repoWorkingHours.findOne(id);
    }

    /**
     * Get all employees from WorkingHours for date
     * @param date - date
     * @return - list employees
     */
    @Override
    public List<Employees> findAllEmployeeForDate(Date date) {
        return repoWorkingHours.findAllEmployeeForDate(date);
    }

    /**
     * Get all employees that avalible to work for date
     * @param date - date
     * @return - list employees
     */
    @Override
    public List<Employees> getAvailableEmployeesForDate(Date date) {
        List<Employees> allEmployeesList = employeesService.getAll();
        List<Employees> notAvailableEmployeesList = findAllEmployeeForDate(date);
        List<Employees> availableEmployees = new ArrayList<>();

        for (int i = 0; i < allEmployeesList.size(); i++) {
            for (int j = 0; j < notAvailableEmployeesList.size(); j++) {

                if (allEmployeesList.get(i) != notAvailableEmployeesList.get(j)) {
                    availableEmployees.add(allEmployeesList.get(i));
                }
            }
        }
        return availableEmployees;
    }

    /**
     * Get List<WorkingHours> for Employee with id
     * @param employeeId - employee's ID
     * @return - list WorkingHours
     */
    @Override
    public List<WorkingHours> getAllWithEmployeeId(Long employeeId) {
        return repoWorkingHours.findAllWithEmployeeId(employeeId);
    }

    /**
     * Get list workingHours for employee and period of date during startDate and endDarte
     * @param startDate - start date
     * @param endDate - end date
     * @param employeeId - employee's ID
     * @return - list workingHours
     */
    @Override
    public List<WorkingHours> getAllForPeriodAndEployee(Date startDate, Date endDate, Long employeeId) {
        return repoWorkingHours.findAllForPeriodAndEployee(startDate,endDate,employeeId);
    }

}

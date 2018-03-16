package net.restapp.servise.impl;

import net.restapp.exception.EntityConstraintException;
import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.repository.RepoWorkingHours;
import net.restapp.servise.CountService;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ConstantException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    @Autowired
    RepoWorkingHours repoWorkingHours;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    CountService countService;

    @Transactional
    @Override
    public void save(WorkingHours workingHours) {
        if (workingHours.getStatus().getId() > 1 && workingHours.getStatus().getId() < 6){
            if (workingHours.getEvent().getId() != 1) {
                throw new EntityConstraintException("for 1 < statusid < 6 event id must be equal 1");
            }
        }
        BigDecimal salary = countService.calculatePaymentOfEvent(workingHours);
        workingHours.setSalary(salary);
        repoWorkingHours.save(workingHours);
    }

    @Override
    public void delete(Long id) {
        repoWorkingHours.delete(id);
    }

    @Override
    public List<WorkingHours> getAll() {
        return repoWorkingHours.findAll();
    }

    @Override
    public WorkingHours getById(Long id) {
        return repoWorkingHours.findOne(id);
    }

    @Override
    public List<Employees> findAllEmployeeForDate(Date date) {
        return repoWorkingHours.findAllEmployeeForDate(date);
    }

    @Override
    public Integer getAvailableVacationDay(Long id) {
        return employeesService.getAvailableVacationDay(id);
    }

    /**
     * Get List<Employee> that available for date
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
     */
    @Override
    public List<WorkingHours> getAllWithEmployeeId(Long employeeId) {
        return repoWorkingHours.findAllWithEmployeeId(employeeId);
    }

    @Override
    public List<WorkingHours> getAllForPeriodAndEployee(Date startDate, Date startDate1, Long employeeId) {
        return repoWorkingHours.getAllForPeriodAndEployee(startDate,startDate1,employeeId);
    }

}

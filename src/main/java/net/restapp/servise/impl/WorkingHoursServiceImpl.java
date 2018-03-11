package net.restapp.servise.impl;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.repository.RepoWorkingHours;
import net.restapp.servise.CountService;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void save(WorkingHours workingHours) {
        BigDecimal salary = countService.calculateSalaryOfEvent(workingHours);
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
        return repoWorkingHours.getAvailableVacationDay(id);
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

}

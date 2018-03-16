package net.restapp.servise;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;

import java.util.Date;
import java.util.List;

public interface WorkingHoursService extends IService<WorkingHours>{

    /**
     * Get all employees for date from WorkingHours
     * @param date - date
     * @return - list employees
     */
    List<Employees> findAllEmployeeForDate(Date date);

    /**
     * Get employees that available for date
     * @param date - date
     * @return - list of employees
     */
    List<Employees> getAvailableEmployeesForDate(Date date);

    /**
     * Get all workingHours for employee with ID
     * @param employeeId - employee's ID
     * @return - list workingHours
     */
    List<WorkingHours> getAllWithEmployeeId(Long employeeId);

    /**
     * Get all workingHour for employee and period date between startDate and endDate
     * @param startDate - start date
     * @param endDate - end date
     * @param employeeId - employee's ID
     * @return - list workingHours
     */
    List<WorkingHours> getAllForPeriodAndEployee(Date startDate, Date endDate, Long employeeId);


}

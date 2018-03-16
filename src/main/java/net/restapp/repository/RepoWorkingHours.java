package net.restapp.repository;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface RepoWorkingHours extends JpaRepository<WorkingHours, Long> {

    /**
     * Find all employees for date
     * @param date - date
     * @return - list employees
     */
    @Query("select wh.employees FROM WorkingHours wh where wh.startTime = ?1")
    List<Employees> findAllEmployeeForDate(Date date);


    /**
     * Find all workingHours for employee
     * @param employeeId - employee's ID
     * @return - list workingHours
     */
    @Query(value = "SELECT * FROM workinghours WHERE employees_id = ?1", nativeQuery = true)
    List<WorkingHours> findAllWithEmployeeId(Long employeeId);

    /**
     * Find all workingHours for employee during startDate and endDate
     * @param startDate - start date
     * @param endDate - end date
     * @param employeeId - employee's ID
     * @return - list workingHours
     */
    @Query(value = "SELECT * FROM workinghours WHERE start_time between ?1 and ?2 and employees_id = ?3", nativeQuery = true)
    List<WorkingHours> findAllForPeriodAndEployee(Date startDate, Date endDate, Long employeeId);

    /**
     * Get all workingHour for employee and period date between startDate and endDate
     * @param date - date
     * @param employee - employee
     * @return - list workingHours
     */
    @Query("select u FROM WorkingHours u where u.startTime =?1 and u.employees = ?2")
    List<WorkingHours> findEmployeeForDate(Date date, Employees employee);

    @Query("select sum(wh.hours) FROM WorkingHours wh where wh.startTime = ?1 and wh.employees = ?2")
    BigDecimal getSumWorkingHourForEmployeeAndDate(Date date, Employees employees);

}

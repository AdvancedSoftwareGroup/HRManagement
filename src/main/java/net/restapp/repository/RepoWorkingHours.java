package net.restapp.repository;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RepoWorkingHours extends JpaRepository<WorkingHours, Long> {
    @Query("select wh.employees FROM WorkingHours wh where wh.startTime = ?1")
    List<Employees> findAllEmployeeForDate(Date date);

    @Query(value = "SELECT available_vacation_day FROM employees WHERE id = ?1", nativeQuery = true)
    Integer getAvailableVacationDay(Long id);

    @Query(value = "SELECT * FROM workinghours WHERE employees_id = ?1", nativeQuery = true)
    List<WorkingHours> findAllWithEmployeeId(Long employeeId);

    @Query(value = "SELECT * FROM workinghours WHERE start_time between ?1 and ?2 and employees_id = ?3", nativeQuery = true)
    List<WorkingHours> getAllForPeriodAndEployee(Date startDate, Date endDate, Long employeeId);


}

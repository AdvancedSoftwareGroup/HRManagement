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

    @Query("select em FROM Employees em where em.id = ?1")
    Integer getAvailableVacationDay(Long id);

    @Query(value = "SELECT * FROM workinghours WHERE employees_id = ?1", nativeQuery = true)
    List<WorkingHours> findAllWithEmployeeId(Long employeeId);
}

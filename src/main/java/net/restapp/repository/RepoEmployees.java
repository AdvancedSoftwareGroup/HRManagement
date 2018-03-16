package net.restapp.repository;

import net.restapp.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoEmployees extends JpaRepository<Employees,Long> {

    /**
     * Find employees with position ID
     * @param id - position ID
     * @return - employee
     */
    @Query(value = "SELECT * FROM employees WHERE position_id = ?1", nativeQuery = true)
    Employees findAllWithPositionId(Long id);

    /**
     * Get available vacation days by employee's ID
     * @param id - employee's ID
     * @return - available vacation days
     */
    @Query(value = "SELECT available_vacation_day FROM employees WHERE id = ?1", nativeQuery = true)
    Integer findAvailableVacationDay(Long id);
}


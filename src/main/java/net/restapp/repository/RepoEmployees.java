package net.restapp.repository;

import net.restapp.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoEmployees extends JpaRepository<Employees,Long> {

    @Query(value = "SELECT * FROM employees WHERE position_id = ?1", nativeQuery = true)
    Employees findAllWithPositionId(Long id);

}


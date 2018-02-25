package net.restapp.repository;

import net.restapp.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoEmployees extends JpaRepository<Employees,Long> {
}

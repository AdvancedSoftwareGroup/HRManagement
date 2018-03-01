package net.restapp.repository;

import net.restapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoDepartment extends JpaRepository<Department,Long> {



}

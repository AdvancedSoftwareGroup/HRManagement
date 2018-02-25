package net.restapp.repository;

import net.restapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRole extends JpaRepository<Role,Long>{
}

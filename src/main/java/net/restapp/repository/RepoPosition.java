package net.restapp.repository;

import net.restapp.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoPosition extends JpaRepository<Position,Long> {

}

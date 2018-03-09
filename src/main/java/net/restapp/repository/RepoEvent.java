package net.restapp.repository;

import net.restapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoEvent extends JpaRepository<Event,Long>{
}

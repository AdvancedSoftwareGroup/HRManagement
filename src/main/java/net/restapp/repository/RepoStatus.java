package net.restapp.repository;
import net.restapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoStatus extends  JpaRepository<Status,Long> {

    /**
     * Find Status by name
     * @param name - status's name
     * @return - status
     */
    Status findByName(String name);
}

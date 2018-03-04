package net.restapp.servise;

import net.restapp.model.Department;
import net.restapp.model.Position;
import net.restapp.repository.RepoDepartment;
import net.restapp.repository.RepoPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    RepoPosition repoPosition;

    @Autowired
    RepoDepartment repoDepartment;

    @Override
    public void save(Position position) {
        Long departmentId = position.getDepartment().getId();
        Department department = repoDepartment.findOne(departmentId);

        if (department == null) {
            throw new EntityNotFoundException(
                    "department with id="+departmentId +" don't present in database");
        }

        repoPosition.save(position);
    }

    @Override
    public void delete(Long id) {
        repoPosition.delete(id);
    }

    @Override
    public List<Position> getAll() {
        return repoPosition.findAll();
    }

    @Override
    public Position getById(Long id) {
        return repoPosition.findOne(id);
    }
}

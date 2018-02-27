package net.restapp.servise;

import net.restapp.model.Position;
import net.restapp.repository.RepoPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    RepoPosition repoPosition;
    @Override
    public void save(Position position) {
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

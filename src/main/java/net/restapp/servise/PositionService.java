package net.restapp.servise;

import net.restapp.model.Position;

import java.util.List;

public interface PositionService {

    void save(Position position);

    void delete(Long id);

    List<Position> getAll();

    Position getById(Long id);

}

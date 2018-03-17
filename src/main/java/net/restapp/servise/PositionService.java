package net.restapp.servise;

import net.restapp.model.Position;

/**
 * Interface for service's layer of position. Extends CRUD methods from {@link net.restapp.servise.IService}
 */
public interface PositionService extends IService<Position> {

    /**
     * Check are positions have employee
     * @param id - position id
     * @return - if positions don't have employee return true. In ather case return false
     */
    boolean isPositionFree(Long id);
}

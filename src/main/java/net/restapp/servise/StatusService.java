package net.restapp.servise;

import net.restapp.model.Status;

import java.util.List;

/**
 * Interface for service's layer of status. Extends CRUD methods from {@link net.restapp.servise.IService}
 */
public interface StatusService extends IService<Status>{

    /**
     * Find status by name ant return true if it exist
     */
    boolean isStatusExist(Status status);

    /**
     * The method calls a repository's method for find a status by name
     */
    Status findByName(String name);
}

package net.restapp.servise.impl;

import net.restapp.model.Status;
import net.restapp.repository.RepoStatus;
import net.restapp.servise.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service's layer of application for Status, implements {@link net.restapp.servise.StatusService}
 */
@Service
public class StatusServiceImpl implements StatusService {

    /**
     * The field of Status repository's layer that is called for use it's methods
     */
    @Autowired
    RepoStatus repoStatus;


    /**
     * The method calls a repository's method for save a status
     *
     * @param status - status
     */
    @Override
    public void save(Status status) {
        if (status != null) {
            if (status.getId() < 6 && status.getId() != 0)
                throw new IllegalArgumentException("cant change Status with id <=5");
        }
        repoStatus.save(status);
    }

    /**
     * The method calls repository's method for delete a status by ID
     *
     * @param id - status's ID
     */

    @Override
    public void delete(Long id) {
        if (id != null) {
            if (id < 6 && id != 0) throw new IllegalArgumentException("cant delete Status with id <=5");
        }
        repoStatus.delete(id);
    }

    /**
     * The method calls repository's method for find all status
     */
    @Override
    public List<Status> getAll() {
        return repoStatus.findAll();
    }

    /**
     * The method calls repository's method for get one status by ID
     *
     * @param id - status's ID
     */
    @Override
    public Status getById(Long id) {
        return repoStatus.findOne(id);
    }

    /**
     * The method calls a repository's method for find a status by name
     *
     * @param name - status name
     */
    @Override
    public Status findByName(String name) {
        return repoStatus.findByName(name);
    }

    /**
     * Find status by name and return true if it exist
     *
     * @param status - status
     */
    @Override
    public boolean isStatusExist(Status status) {
        return findByName(status.getName()) != null;
    }

}

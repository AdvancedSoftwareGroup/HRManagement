package net.restapp.servise.impl;

import net.restapp.model.Status;
import net.restapp.repository.RepoStatus;
import net.restapp.servise.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    RepoStatus repoStatus;

    @Override
    public void save(Status status) throws Exception {
        if (status.getId() < 6 && status.getId() != 0) throw new Exception("cant change Status with id <=5");
        repoStatus.save(status);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (id < 6) throw new Exception("cant delete Status with id <=5");
        repoStatus.delete(id);
    }

    @Override
    public List<Status> getAll() {
        return repoStatus.findAll();
    }

    @Override
    public Status getById(Long id) {
        return repoStatus.findOne(id);
        }

    @Override
    public Status findByName(String name) {
        return repoStatus.findByName(name);
    }

    @Override
    public boolean isStatusExist(Status status) {
        return findByName(status.getName())!=null;
    }

}

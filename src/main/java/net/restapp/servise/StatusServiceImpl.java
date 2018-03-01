package net.restapp.servise;

import net.restapp.repository.RepoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Status;
import java.util.List;


@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    RepoStatus repoStatus;

    @Override
    public void save(net.restapp.model.Status status) {
        repoStatus.save(status);
    }

    @Override
    public void delete(Long id) {
        repoStatus.delete(id);
    }

    @Override
    public List<net.restapp.model.Status> getAll() {
        return repoStatus.findAll();
    }

    @Override
    public net.restapp.model.Status getById(Long id) {
        return repoStatus.findOne(id);
        }

    @Override
    public net.restapp.model.Status findByName(String name) {
        return repoStatus.findByName(name);
    }

    @Override
    public boolean isStatusExist(net.restapp.model.Status status) {
        return findByName(status.getName())!=null;
    }

}

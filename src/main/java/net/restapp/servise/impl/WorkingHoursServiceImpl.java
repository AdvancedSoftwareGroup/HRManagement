package net.restapp.servise.impl;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.repository.RepoWorkingHours;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    @Autowired
    RepoWorkingHours repoWorkingHours;

    @Override
    public void save(WorkingHours workingHours) {
        repoWorkingHours.save(workingHours);
    }

    @Override
    public void delete(Long id) {
        repoWorkingHours.delete(id);
    }

    @Override
    public List<WorkingHours> getAll() {
        return repoWorkingHours.findAll();
    }

    @Override
    public WorkingHours getById(Long id) {
        return repoWorkingHours.findOne(id);
    }

    @Override
    public List<Employees> findAllEmployeeForDate(Date date) {
        return repoWorkingHours.findAllEmployeeForDate(date);
    }

    @Override
    public Integer getAvailableVacationDay(Long id) {
        return repoWorkingHours.getAvailableVacationDay(id);
    }



}

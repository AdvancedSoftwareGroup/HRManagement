package net.restapp.servise.impl;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.model.Status;
import net.restapp.repository.RepoArchiveSalary;
import net.restapp.repository.RepoStatus;
import net.restapp.servise.ArchiveSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ArchiveSalaryImpl implements ArchiveSalaryService {

    @Autowired
    RepoArchiveSalary repoArchiveSalary;

    @Override
    public void save(ArchiveSalary archiveSalary) {
        repoArchiveSalary.save(archiveSalary);
    }

    @Override
    public void delete(Long id) {
        repoArchiveSalary.delete(id);
    }

    @Override
    public List<ArchiveSalary> getAll() {
        return repoArchiveSalary.findAll();
    }

    @Override
    public ArchiveSalary getById(Long id) {
        return repoArchiveSalary.findOne(id);
    }

    @Override
    public List<ArchiveSalary> findDateBetween(Date startDate, Date endDate, Employees employee) {
        return repoArchiveSalary.findDateBetween(startDate, endDate, employee);
    }

    @Override
    public ArchiveSalary findSalaryViaDate(Date salaryDate, Employees employee) {
        return repoArchiveSalary.findSalaryViaDate(salaryDate, employee);
    }

    @Override
    public List<ArchiveSalary> getAllViaDate(Date date) {
        return repoArchiveSalary.getAllByDate(date);
    }

}

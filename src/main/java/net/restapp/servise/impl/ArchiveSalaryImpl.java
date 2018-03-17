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

/**
 * The service's layer of application for ArchiveSalary, contains CRUD methods
 */
@Service
public class ArchiveSalaryImpl implements ArchiveSalaryService {

    /**
     * The field of ArchiveSalary repository's layer that is called for use it's methods
     */
    @Autowired
    RepoArchiveSalary repoArchiveSalary;

    /**
     *  The method calls a repository's method for save a archiveSalary
     * @param archiveSalary - archiveSalary
     */
    @Override
    public void save(ArchiveSalary archiveSalary) {
        repoArchiveSalary.save(archiveSalary);
    }

    /**
     * The method calls a repository's method for delete a archiveSalary by ID
     * @param id - archiveSalary's ID
     */
    @Override
    public void delete(Long id) {
        repoArchiveSalary.delete(id);
    }

    /**
     * The method calls a repository's method for get all archiveSalarys
     * @return - list archiveSalary
     */
    @Override
    public List<ArchiveSalary> getAll() {
        return repoArchiveSalary.findAll();
    }

    /**
     * The method calls a repository's method for get one archiveSalary by ID
     * @param id - archiveSalary's ID
     * @return - archiveSalary
     */
    @Override
    public ArchiveSalary getById(Long id) {
        return repoArchiveSalary.findOne(id);
    }

    /**
     * The method calls a repository's method find all for employee between startDate and endDate
     * @param startDate - start date
     * @param endDate - end date
     * @param employee - employee
     * @return - list archiveSalary
     */
    @Override
    public List<ArchiveSalary> findDateBetween(Date startDate, Date endDate, Employees employee) {
        return repoArchiveSalary.findDateBetween(startDate, endDate, employee);
    }

    /**
     * The method calls a repository's method get one archiveSalary for employee and date
     * @param salaryDate - date
     * @param employee - employee
     * @return - archiveSalary
     */
    @Override
    public ArchiveSalary findSalaryViaDate(Date salaryDate, Employees employee) {
        return repoArchiveSalary.findSalaryViaDate(salaryDate, employee);
    }

    /**
     * The method calls a repository's method get all for date
     * @param date - date
     * @return - list archiveSalary
     */
    @Override
    public List<ArchiveSalary> getAllViaDate(Date date) {
        return repoArchiveSalary.getAllByDate(date);
    }

}

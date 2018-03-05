package net.restapp.servise;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.Department;
import net.restapp.model.Employees;

import java.util.Date;
import java.util.List;

public interface ArchiveSalaryService {

    void save(ArchiveSalary archiveSalary);

    void delete(Long id);

    List<ArchiveSalary> getAll();

    ArchiveSalary getById(Long id);

    List<ArchiveSalary> findDateBetween(Date startDate, Date endDate, Employees employee);

    ArchiveSalary findSalaryViaDate(Date salaryDate, Employees employee);

    List<ArchiveSalary> getAllViaDate(Date date);

}

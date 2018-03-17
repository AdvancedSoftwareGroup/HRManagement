package net.restapp.servise;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.Department;
import net.restapp.model.Employees;

import java.util.Date;
import java.util.List;

public interface ArchiveSalaryService {

    /**
     *  Save a archiveSalary
     * @param archiveSalary - archiveSalary
     */
    void save(ArchiveSalary archiveSalary);

    /**
     * delete a archiveSalary by ID
     * @param id - archiveSalary's ID
     */
    void delete(Long id);

    /**
     * get all archiveSalarys
     * @return - list archiveSalary
     */
    List<ArchiveSalary> getAll();

    /**
     * get one archiveSalary by ID
     * @param id - archiveSalary's ID
     * @return - archiveSalary
     */
    ArchiveSalary getById(Long id);

    /**
     * find all for employee between startDate and endDate
     * @param startDate - start date
     * @param endDate - end date
     * @param employee - employee
     * @return - list archiveSalary
     */
    List<ArchiveSalary> findDateBetween(Date startDate, Date endDate, Employees employee);

    /**
     * get one archiveSalary for employee and date
     * @param salaryDate - date
     * @param employee - employee
     * @return - archiveSalary
     */
    ArchiveSalary findSalaryViaDate(Date salaryDate, Employees employee);

    /**
     * get all for date
     * @param date - date
     * @return - list archiveSalary
     */
    List<ArchiveSalary> getAllViaDate(Date date);

}

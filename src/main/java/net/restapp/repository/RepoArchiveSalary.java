package net.restapp.repository;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface RepoArchiveSalary extends JpaRepository <ArchiveSalary, Long> {

    /**
     * Get all from archive_salary table for employee where date between statDate and endDate
     * @param startDate - start date
     * @param endDate - end date
     * @param employee - employee
     * @return list of archiveSalary objects
     */
    @Query("select u FROM ArchiveSalary u where u.date between ?1 and ?2 and u.employee = ?3")
    List<ArchiveSalary> findDateBetween(Date startDate, Date endDate, Employees employee);


    /**
     * Get obj from archive_salary table for salary date end employee
     * @param salaryDate - searching date
     * @param employee - searching employee
     * @return obj of archiveSalary
     */
    @Query("select u FROM ArchiveSalary u where u.date =?1 and u.employee = ?2")
    ArchiveSalary findSalaryViaDate(Date salaryDate, Employees employee);

    /**
     * Get all for date
     * @param date - date
     * @return list of archiveSalary
     */
    List<ArchiveSalary> getAllByDate(Date date);

}

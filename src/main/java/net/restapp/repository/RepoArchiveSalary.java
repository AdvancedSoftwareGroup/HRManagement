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

    @Query("select u FROM ArchiveSalary u where u.date between ?1 and ?2 and u.employee = ?3")
    List<ArchiveSalary> findDateBetween(Date startDate, Date endDate, Employees employee);

    @Query("select u FROM ArchiveSalary u where u.date =?1 and u.employee = ?2")
    ArchiveSalary findSalaryViaDate(Date salaryDate, Employees employee);

    List<ArchiveSalary> getAllByDate(Date date);

}

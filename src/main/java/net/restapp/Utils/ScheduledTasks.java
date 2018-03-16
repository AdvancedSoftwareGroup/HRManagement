package net.restapp.Utils;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.EmployeeSheet;
import net.restapp.model.Employees;
import net.restapp.servise.ArchiveSalaryService;
import net.restapp.servise.CountService;
import net.restapp.servise.EmailService;
import net.restapp.servise.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Class with tasks for Scheduled
 */

@Component
public class ScheduledTasks {

    /**
     * The field of ArchiveSalary service's layer that is called for use it's methods
     */
    @Autowired
    ArchiveSalaryService archiveSalaryService;

    /**
     * The field of Employee service's layer that is called for use it's methods
     */
    @Autowired
    EmployeesService employeesService;

    /**
     * The field of Count service that is called for use it's methods
     */
    @Autowired
    CountService countService;

    /**
     * The field of Email service that is called for use it's methods
     */
    @Autowired
    private EmailService emailService;


    /**
     * Run calculating salary for each employee and send emails with sheets
     */
    @Scheduled(cron = "0 0 0 1 * *")
    public void calculateSalary() {

        List<Employees> employeesList = employeesService.getAll();

        for (Employees employee: employeesList) {
            EmployeeSheet employeeSheet = countService.calculateEmployeeSheet(employee);
            //save archive salary
            ArchiveSalary archiveSalary = new ArchiveSalary();
            archiveSalary.setEmployee(employeeSheet.getEmployees());
            archiveSalary.setDate(employeeSheet.getDate());
            archiveSalary.setMonthSalary(employeeSheet.getTotalSalary());
            archiveSalaryService.save(archiveSalary);

//            //send email
           String mail = employeeSheet.getEmployees().getUser().getEmail();
           String sendingMessage = "You salary for last month is " + employeeSheet.getTotalSalary();
           emailService.sendEmail(mail,"You slary",sendingMessage);
        }
    }

    /**
     * Update available vacation days for each employee after it works a year
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void vacantionDay() {
        List<Employees> list = employeesService.getAll();
        if (list.isEmpty()) {
            System.out.println("Eror in sheduler vacanyionDay, can't get list of employee");
            return;
        }

        Date currentDate =  new Date();


        for (Employees e : list) {
            long oneYearInMilisec;
            oneYearInMilisec = 31536000000L;
            long diff = (currentDate.getTime() - e.getStartWorkingDate().getTime());
            if(diff >= oneYearInMilisec){
                e.setAvailableVacationDay(21);
            }
            if(diff == oneYearInMilisec/2){
                e.setAvailableVacationDay(11);
            }
        }
    }
}
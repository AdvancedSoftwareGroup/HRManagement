package net.restapp.Utils;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.EmployeeSheet;
import net.restapp.model.Employees;
import net.restapp.servise.ArchiveSalaryService;
import net.restapp.servise.CountService;
import net.restapp.servise.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    ArchiveSalaryService archiveSalaryService;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    CountService countService;

    @Autowired
    private JavaMailSender sender;


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

            //send email
            if (employee.getId()== 2) {
                Email emailOb = new Email();
                String mail = employeeSheet.getEmployees().getUser().getEmail();
                String sendingMessage = "You salary for last month is " + employeeSheet.getTotalSalary();
                emailOb.sendEmail(sender, mail, sendingMessage);
            }
        }
    }


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
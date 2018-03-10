package net.restapp.Utils;

import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.servise.ArchiveSalaryService;
import net.restapp.servise.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    ArchiveSalaryService archiveSalaryService;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    private JavaMailSender sender;

    @Scheduled(cron = "0 0 0 1 * *")
    public void reportCurrentTime() {

        Email emailOb = new Email();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        List<ArchiveSalary> archiveSalaries = archiveSalaryService.getAllViaDate(result);

        for (ArchiveSalary value : archiveSalaries) {
            String mail = value.getEmployee().getUser().getEmail();
            String sendingMessage = value.getEmployee().getFirstName() + value.getEmployee().getLastName()
                    + value.getDate() + value.getMonthSalary();
            //Тут должна быть отправка по почте
            emailOb.sendEmail(sender, mail, sendingMessage);
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
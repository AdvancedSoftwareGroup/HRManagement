package net.restapp.Utils;

import net.restapp.model.EmployeeSheet;
import net.restapp.model.Event;
import net.restapp.model.Status;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Messages for emails
 */
public class LettersExample {

    /**
     * create message for welcome letter
     * @param url - domain url
     * @return - message
     */
    public String createWelcomeMessage(String url) {

        return "<p>Dear, employee.</p> " +
                "<p>Congratulations you with authorization in HRManager. </p>" +
                "<p>You can access the data by using your email as login. </p><br><br>" +
                "<p>We strongly recommend changing the password. To do this, follow the link "+
                url + "/employees/user/pass </p>";
    }

    /**
     * create message for employee's month salary
     * @param employeeSheet - employee sheet
     * @return - employee sheet like message
     */
    public String createSalaryMessage(EmployeeSheet employeeSheet){
        SimpleDateFormat format = new SimpleDateFormat("MMMM  yyyy");
        String message = "<p>Dear, "+employeeSheet.getEmployees().getFirstName()+" "+employeeSheet.getEmployees().getLastName()+"</p>";
        message += "<p>Your total salary for "+format.format(employeeSheet.getDate())+" is: "+employeeSheet.getTotalSalary()+"</p>";
        message += "<p><div>Your statistic:</div><ol>";
        for (Map.Entry<Status, BigDecimal> entry : employeeSheet.getStatusHours().entrySet())
        {
            message += "<li>"+ entry.getKey().getName()+" total hours is "+entry.getValue()+"</li>";
        }
        message +="</ol></p>";

        message += "<p><div>List Your events:</div><ol>";
        for (Event event : employeeSheet.getListEvents()) {
            message += "<li>"+event.getName()+"</li>";
        }
        message +="</ol></p>";
        return message;
    }


}

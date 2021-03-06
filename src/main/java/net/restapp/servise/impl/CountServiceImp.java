package net.restapp.servise.impl;

import net.restapp.exception.NotEnoughHoursException;
import net.restapp.model.*;
import net.restapp.servise.ArchiveSalaryService;
import net.restapp.servise.CountService;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.*;

/**
 * Class use for calculation payment for event, month salary
 */

@Service
public class CountServiceImp implements CountService {

    /**
     * The field of WorkingHours service's layer that is called for use it's methods
     */
    @Autowired
    WorkingHoursService workingHoursService;

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
     * Constants
     */
    private final static int WORK_HOURS_PER_DAY = 8;
    private final static int AVERAGE_WORKING_DAY_PER_MONTH = 21;
    private final static BigDecimal SICK_COEF_TO_THREE_YEAR = BigDecimal.valueOf(0.5);
    private final static BigDecimal SICK_COEF_TO_FIVE_YEAR = BigDecimal.valueOf(0.6);
    private final static BigDecimal SICK_COEF_TO_EIGHT_YEAR = BigDecimal.valueOf(0.7);
    private final static BigDecimal SICK_COEF_OVER_EIGHT_YEAR = BigDecimal.valueOf(1);
    private final static int COUNT_MONTH_FOR_CALCULATE_HOSPITAL_PAYMENT = 6;
    private final static int COUNT_MONTH_FOR_CALCULATE_VACATION_PAYMENT = 12;

    /**
     * Calculate salary for event with status per hours
     *
     * @param workingHours - workingHours
     * @return - salary for event
     */
    public BigDecimal calculatePaymentOfEvent(WorkingHours workingHours) {

        long statusId = workingHours.getStatus().getId();
        BigDecimal salary;

        if (statusId == 2) {
            BigDecimal availableHours = BigDecimal.valueOf(
                    employeesService.getAvailableVacationDay(workingHours.getEmployees().getId())
                            * WORK_HOURS_PER_DAY);
            if (availableHours.compareTo(workingHours.getHours()) < 0) {
                throw new NotEnoughHoursException("Don't enough hours fo vacation. Available hours=" + availableHours);
            }
            salary = calculatePaymentForVacation(workingHours);
        } else if (statusId == 3) {
            salary = calculatePaymentForHospital(workingHours);
        } else {
            BigDecimal salaryPerHour = workingHours.getEmployees().getPosition().getSalary();
            BigDecimal statusCoef = workingHours.getStatus().getSalary_coef();
            BigDecimal eventCoef = workingHours.getEvent().getSalary_coef();
            BigDecimal hours = workingHours.getHours();
            salary = eventCoef.multiply(hours.multiply(salaryPerHour.multiply(statusCoef)));
        }

        return salary;
    }

    /**
     * Calculate month salary and create salarySheet for Employee
     *
     * @param employees - employee
     * @return - employee sheet
     */
    @Override
    public EmployeeSheet calculateEmployeeSheet(Employees employees) {

        Calendar myCal = Calendar.getInstance();
        myCal.add(Calendar.MONTH, -1);
        myCal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = myCal.getTime();

        int dayInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        myCal.set(Calendar.DAY_OF_MONTH, dayInMonth);
        Date endDate = myCal.getTime();

        List<WorkingHours> list = workingHoursService.getAllForPeriodAndEployee(startDate, endDate, employees.getId());

        BigDecimal sum = BigDecimal.valueOf(0);
        EmployeeSheet employeeSheet = new EmployeeSheet();
        Map<Status, BigDecimal> statusHours = new HashMap<>();
        List<Event> listEvents = new ArrayList<>();
        for (WorkingHours wh : list) {

            BigDecimal bg = statusHours.get(wh.getStatus());
            if (bg == null) bg = BigDecimal.valueOf(0);
            statusHours.put(wh.getStatus(), bg.add(wh.getHours()));
            if (wh.getEvent().getId() != 1) {
                listEvents.add(wh.getEvent());
            }
            sum = sum.add(wh.getSalary());
        }

        employeeSheet.setDate(startDate);
        employeeSheet.setEmployees(employees);
        employeeSheet.setTotalSalary(sum);
        employeeSheet.setListEvents(listEvents);
        employeeSheet.setStatusHours(statusHours);

        return employeeSheet;
    }


    /**
     * Calculate salary for employee with status Hospital
     *
     * @param workingHours - workingHours
     * @return salary for hours from workingHours.getHour.
     */
    private BigDecimal calculatePaymentForHospital(WorkingHours workingHours) {
        BigDecimal middleSalary = getMiddleSalaryForPeriod(workingHours, COUNT_MONTH_FOR_CALCULATE_HOSPITAL_PAYMENT);
        BigDecimal middleSalWithExp = getMiddleHospitalSalaryIncludeWorkExperience(workingHours, middleSalary);
        BigDecimal paymentPerHour = paymentPerHour(middleSalWithExp);
        return paymentPerHour.multiply(workingHours.getHours());
    }

    /**
     * Calculate hospital payment depends of working experience
     *
     * @param workingHours - workingHours
     * @param middleSalary - middle salary for period
     * @return - hospital payment per month
     */
    private BigDecimal getMiddleHospitalSalaryIncludeWorkExperience(WorkingHours workingHours, BigDecimal middleSalary) {
        Date startWorking = workingHours.getEmployees().getStartWorkingDate();
        long countMonthWorkingHere = monthsBetween(startWorking, Calendar.getInstance().getTime());
        long workingMonth = countMonthWorkingHere + workingHours.getEmployees().getExperience();
        long workingYears = workingMonth / 12;
        BigDecimal salary;

        if (workingYears > 8) {
            salary = middleSalary.multiply(SICK_COEF_OVER_EIGHT_YEAR);
        } else {
            if (workingYears > 5) {
                salary = middleSalary.multiply(SICK_COEF_TO_EIGHT_YEAR);
            } else {
                if (workingYears > 3) {
                    salary = middleSalary.multiply(SICK_COEF_TO_FIVE_YEAR);
                } else {
                    salary = middleSalary.multiply(SICK_COEF_TO_THREE_YEAR);
                }
            }
        }
        return salary;
    }

    /**
     * Return count month between two dates
     *
     * @param startDate - start date
     * @param endDate   - end date
     * @return - count of month
     */
    private long monthsBetween(Date startDate, Date endDate) {
        final Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        final Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        return (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
    }

    /**
     * Calculate payment for employee with status vacation
     *
     * @param workingHours - workingHours
     * @return -salary for hours from workingHours.getHour.
     */
    private BigDecimal calculatePaymentForVacation(WorkingHours workingHours) {
        BigDecimal middleSalary = getMiddleSalaryForPeriod(workingHours, COUNT_MONTH_FOR_CALCULATE_VACATION_PAYMENT);

        BigDecimal salaryPerHour = paymentPerHour(middleSalary);

        return salaryPerHour.multiply(workingHours.getHours());
    }

    /**
     * Convert salary from salary per month to salary per hours
     *
     * @param salary - salary per month
     * @return - salary per hour
     */
    private BigDecimal paymentPerHour(BigDecimal salary) {
        BigDecimal hour = BigDecimal.valueOf(WORK_HOURS_PER_DAY * AVERAGE_WORKING_DAY_PER_MONTH);
        return salary.divide(hour, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculate middle salary for last countMonth for employee
     *
     * @param workingHours - workingHours
     * @param countMonth   - count of month
     * @return - middle month salary for employee
     */
    private BigDecimal getMiddleSalaryForPeriod(WorkingHours workingHours, int countMonth) {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.MONTH, -countMonth);

        List<ArchiveSalary> list = archiveSalaryService.findDateBetween(cal.getTime(), today, workingHours.getEmployees());
        BigDecimal sum = BigDecimal.valueOf(0);

        for (ArchiveSalary archive : list) {
            sum = sum.add(archive.getMonthSalary());
        }
        return sum.divide(BigDecimal.valueOf(list.size()), 2, BigDecimal.ROUND_HALF_UP);
    }
}

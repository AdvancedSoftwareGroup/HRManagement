package net.restapp.servise.impl;

import net.restapp.exception.NotEnoughHoursException;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.WorkingHours;
import net.restapp.servise.ArchiveSalaryService;
import net.restapp.servise.CountService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CountServiceImp implements CountService {

    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    ArchiveSalaryService archiveSalaryService;

    private final static int WORK_HOURS_PER_DAY = 8;
    private final static int AVERAGE_WORKING_DAY_PER_MONTH = 21;

    /**
     * Calculate salary for event with status per hours
     */
    public BigDecimal calculatePaymentOfEvent(WorkingHours workingHours) {

        long statusId = workingHours.getStatus().getId();
        BigDecimal salary;

        if (statusId == 2) {
            BigDecimal availableHours = BigDecimal.valueOf(
                    workingHoursService.getAvailableVacationDay(workingHours.getEmployees().getId())
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
     * Calculate salary for employee with status Hospital
     *
     * @return salary for hours from workingHours.getHour.
     */
    private BigDecimal calculatePaymentForHospital(WorkingHours workingHours) {
        BigDecimal middleSalary = getMiddleSalaryForPeriod(workingHours, 6);
        BigDecimal middleSalWithExp = getMiddleHospitalSalaryIncludeWorkExperience(workingHours, middleSalary);
        BigDecimal paymentPerHour = paymentPerHour(middleSalWithExp);
        return paymentPerHour.multiply(workingHours.getHours());
    }

    /**
     * Calculate hospital salary depends of working experience
     */
    private BigDecimal getMiddleHospitalSalaryIncludeWorkExperience(WorkingHours workingHours, BigDecimal middleSalary) {
        Date startWorking = workingHours.getEmployees().getStartWorkingDate();
        long countMonthWorkingHere=monthsBetween(startWorking,Calendar.getInstance().getTime());
        long workingMonth = countMonthWorkingHere+ workingHours.getEmployees().getExperience();
        long workingYears = workingMonth/12;
        BigDecimal salary;

        if (workingYears > 8){
            salary = middleSalary;
        } else {
            if (workingYears > 5){
                salary = calculatePercentageOfNumber(middleSalary,BigDecimal.valueOf(70));
            } else {
                if (workingYears > 3){
                    salary = calculatePercentageOfNumber(middleSalary,BigDecimal.valueOf(60));
                } else {
                    salary = calculatePercentageOfNumber(middleSalary,BigDecimal.valueOf(50));
                }
            }
        }
        return salary;
    }

    /**
     * Calculate percent of the number
     */
    private BigDecimal calculatePercentageOfNumber(BigDecimal number, BigDecimal percent){
        BigDecimal num = number.multiply(percent);
        return num.divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Return count month between two dates
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
     * @return salary for hours from workingHours.getHour.
     */
    private BigDecimal calculatePaymentForVacation(WorkingHours workingHours) {
        BigDecimal middleSalary = getMiddleSalaryForPeriod(workingHours, 12);

        BigDecimal salaryPerHour = paymentPerHour(middleSalary);

        return salaryPerHour.multiply(workingHours.getHours());
    }

    /**
     * Convert salary from salary per month to salary per hours
     * @param salary - salary per month
     * @return - salary per hour
     */
    private BigDecimal paymentPerHour(BigDecimal salary){
        BigDecimal hour = BigDecimal.valueOf(WORK_HOURS_PER_DAY * AVERAGE_WORKING_DAY_PER_MONTH);
        return salary.divide(hour, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculate middle salary for last count month
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

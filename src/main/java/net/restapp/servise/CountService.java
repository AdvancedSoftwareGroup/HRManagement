package net.restapp.servise;

import net.restapp.model.EmployeeSheet;
import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Interface for class witch use for calculation payment for event, month salary
 */

public interface CountService {

    /**
     * Caclulate payment for event
     * @param workingHours - workingHours
     * @return - salary for event
     */
    BigDecimal calculatePaymentOfEvent(WorkingHours workingHours);

    /**
     * Calculate month salary and create Sheet for employee
     * @param employees - employee
     * @return - month sheet for employee
     */
    EmployeeSheet calculateEmployeeSheet(Employees employees);

}

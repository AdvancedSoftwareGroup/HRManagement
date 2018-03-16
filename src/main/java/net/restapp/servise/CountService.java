package net.restapp.servise;

import net.restapp.model.EmployeeSheet;
import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


public interface CountService {

    BigDecimal calculatePaymentOfEvent(WorkingHours workingHours);

    EmployeeSheet calculateEmployeeSheet(Employees employees);

}

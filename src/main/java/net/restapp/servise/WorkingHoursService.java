package net.restapp.servise;

import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;

import java.util.Date;
import java.util.List;

public interface WorkingHoursService {
    void save(WorkingHours workingHours);

    void delete(Long id);

    List<WorkingHours> getAll();

    WorkingHours getById(Long id);

    List<Employees> findAllEmployeeForDate(Date date);

    Integer getAvailableVacationDay(Long id);

    List<Employees> getAvailableEmployeesForDate(Date date);

    List<WorkingHours> getAllWithEmployeeId(Long employeeId);

    List<WorkingHours> getAllForPeriodAndEployee(Date startDate, Date endDate, Long employeeId);
}

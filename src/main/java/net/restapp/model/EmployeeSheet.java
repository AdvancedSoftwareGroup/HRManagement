package net.restapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class used for month hours statistic and salary
 *
 */
@Getter
@Setter
public class EmployeeSheet {

    private Employees employees;

    private Date date;

    private BigDecimal totalSalary;

    private Map <Status, BigDecimal> statusHours;

    private List<Event> listEvents;
}


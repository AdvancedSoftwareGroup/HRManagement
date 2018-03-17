package net.restapp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

// todo: show statistic for last month
// private List<SheetBody> sheetBodys;
//
//
//    @Getter
//    @Setter
//    public class SheetBody {
//        private String name;
//
//        private int countHours;
//
//        private BigDecimal payment;
//    }
}

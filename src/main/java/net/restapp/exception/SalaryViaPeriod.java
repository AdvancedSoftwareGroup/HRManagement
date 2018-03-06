package net.restapp.exception;

import java.math.BigDecimal;
import java.util.Date;

public class SalaryViaPeriod extends RuntimeException{

   private Date startDate;

   private Date endDate;

   private BigDecimal salary;

    @Override
    public String toString() {
        return "SalaryViaPeriod{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", salary=" + salary +
                '}';
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}

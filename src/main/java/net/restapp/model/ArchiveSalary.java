package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "archiveSalary")
public class ArchiveSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonManagedReference
    private Employees employee;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "monthSalary")
    private BigDecimal monthSalary;

    @Override
    public String toString() {
        return "ArchiveSalary{" +
                "id=" + id +
                ", employee=" + employee +
                ", date=" + date +
                ", monthSalary=" + monthSalary +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(BigDecimal monthSalary) {
        this.monthSalary = monthSalary;
    }
}

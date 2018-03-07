package net.restapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "archive_salary")
@Getter
@Setter
public class ArchiveSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "month_salary")
    private BigDecimal monthSalary;

    @ManyToOne
//    @Column(name = "employee_id")
    private Employees employees;


    @Override
    public String toString() {
        return "ArchiveSalary{" +
                "id=" + id +
                ", employees=" + employees +
                ", date=" + date +
                ", monthSalary=" + monthSalary +
                '}';
    }


}

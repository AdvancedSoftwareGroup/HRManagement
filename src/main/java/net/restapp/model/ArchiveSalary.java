package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "archive_salary")
@Getter
@Setter
@EqualsAndHashCode(exclude = "employee")
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
    //@JsonManagedReference
    private Employees employee;


    @Override
    public String toString() {
        return "ArchiveSalary{" +
                "id=" + id +
                ", employee=" + employee +
                ", date=" + date +
                ", monthSalary=" + monthSalary +
                '}';
    }


}

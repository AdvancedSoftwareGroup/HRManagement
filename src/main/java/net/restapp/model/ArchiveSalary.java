package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "archiveSalary")
@Getter
@Setter
public class ArchiveSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "monthSalary")
    private BigDecimal monthSalary;

    @ManyToOne
    @JsonManagedReference
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

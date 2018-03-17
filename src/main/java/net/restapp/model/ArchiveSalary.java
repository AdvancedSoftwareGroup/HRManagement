package net.restapp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
/**
 * The class implements a set of methods for working
 * with entities of the {@link ArchiveSalary} class.
 */
@Entity
@Table(name = "archive_salary")
@Getter
@Setter
@EqualsAndHashCode(exclude = "employee")
public class ArchiveSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated ID for archive salary entry")
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    @ApiModelProperty(notes = "Date of record salary entry to archive")
    @NotNull
    private Date date;

    @Column(name = "month_salary")
    @ApiModelProperty(notes = "Total salary in current month")
    private BigDecimal monthSalary;

    @ManyToOne
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

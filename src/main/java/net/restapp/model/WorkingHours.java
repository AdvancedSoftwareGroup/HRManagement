package net.restapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workinghours")
@Getter
@Setter
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "startTime")
    @NotNull(message = "This field must be NOT NULL")
    private Date startTime;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "hours")
    @NotNull(message = "This field must be NOT NULL")
    private BigDecimal hours;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Employees employees;

}

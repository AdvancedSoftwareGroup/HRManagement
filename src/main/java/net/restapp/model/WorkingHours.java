package net.restapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name = "date")
    private Date date;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Event event;

    @Column(name = "hours")
    private BigDecimal hours;

    @ManyToOne
    private Employees employees;

}

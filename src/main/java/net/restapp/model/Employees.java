package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
/**
 * The class implements a set of methods for working
 * with entities of the {@link Employees} class.
 */

@Entity
@Table(name = "employees")
@Getter
@Setter
@EqualsAndHashCode(exclude = { "archiveSalary", "workingHoursList", "user", "position"})

public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name ="availableVacationDay")
    private int availableVacationDay;// available day for Vacation

    @NotNull
    @Column(name = "experience")
    //("Experience it's count of month")
    private int experience;

    @NotNull
    @Column(name = "startWorkingDate")
    private Date startWorkingDate;

    @NotNull
    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private  List<ArchiveSalary> archiveSalary;

    @OneToMany(mappedBy = "employees")
    private List<WorkingHours> workingHoursList;

}

package net.restapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name ="availableVacationDay")
    private int availableVacationDay;// available day for Vacation

    @Column(name = "experience")
    private int experience;

    @Column(name = "startWorkingDate")
    private Date startWorkingDate;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "employees")
    private List<WorkingHours> workingHoursList;
}

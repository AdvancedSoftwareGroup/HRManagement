package net.restapp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import net.restapp.json.EmployeesJsonSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")

@Getter
@Setter
@JsonSerialize(using = EmployeesJsonSerializer.class)



public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private  List<ArchiveSalary> archiveSalary;

    @OneToMany(mappedBy = "employees")
    private List<WorkingHours> workingHoursList;

}

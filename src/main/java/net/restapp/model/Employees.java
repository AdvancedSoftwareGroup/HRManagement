package net.restapp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.restapp.json.EmployeesJsonSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")

@Getter
@Setter
@JsonSerialize(using = EmployeesJsonSerializer.class)
@EqualsAndHashCode(exclude = { "archiveSalary", "workingHoursList" })
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
    private int experience;

    @NotNull
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

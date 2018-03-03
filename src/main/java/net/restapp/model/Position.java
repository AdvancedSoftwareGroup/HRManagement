package net.restapp.model;

import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "position")
@Getter
@Setter
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    @Pattern(regexp= RegexpPatterns.patternStringWithNumbersLettersAndDash,
            message = RegexpPatterns.messageStringWithNumbersLettersAndDash)
    private String name;

    @Column(name ="dayForVacation")
    @NotNull(message = "This field must be NOT NULL")
    private int dayForVacation;

    @Column(name = "salary")
    @NotNull(message = "This field must be NOT NULL")
    private BigDecimal salary;

    @OneToMany(mappedBy = "position")
    List<Employees> employeesList;

    @ManyToMany
    @JoinTable(name = "position_department", joinColumns=@JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    List<Department> departmentList;

}

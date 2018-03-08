package net.restapp.model;

import com.sun.istack.internal.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "position")
@Getter
@Setter
@EqualsAndHashCode
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
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "99999.00")
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Nullable
    @OneToOne(mappedBy = "position")
    private Employees employees;

}

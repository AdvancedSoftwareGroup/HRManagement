package net.restapp.model;

import com.sun.istack.internal.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * The class implements a set of methods for working
 * with entities of the {@link Position} class.
 */

@Entity
@Table(name = "position")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"department"})
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    private String name;

    @Column(name ="dayForVacation")
    @NotNull(message = "This field must be NOT NULL")
    private int dayForVacation;

    @Column(name = "salary")
    @NotNull(message = "This field must be NOT NULL")
    private BigDecimal salary;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    @NotNull(message = "This field must be NOT NULL")
    private Department department;

    @Nullable
    @OneToOne(mappedBy = "position")
    private Employees employees;

}

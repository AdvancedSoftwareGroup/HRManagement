package net.restapp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
/**
 * The class implements a set of methods for working
 * with entities of the {@link Status} class.
 */
@Entity
@Table(name = "status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "workingHoursList")
public class Status {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    private String name;

    @Column(name = "salary_coef")
    private BigDecimal salary_coef;

    @OneToMany(mappedBy = "status")
    List<WorkingHours> workingHoursList;


}

package net.restapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
@EqualsAndHashCode(exclude = "workingHoursList")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    private String name;

    @Column(name = "salary_coef")
    private BigDecimal salary_coef;

    @OneToMany(mappedBy = "event")
    List<WorkingHours> workingHoursList;
}

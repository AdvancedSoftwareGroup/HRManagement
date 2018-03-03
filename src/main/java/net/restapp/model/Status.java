package net.restapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

  //  private List<Status> statuses = Arrays.asList(new Status("Workday", "Hospital", "Vacation", "Absent", "Overtime"));

}

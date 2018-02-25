package net.restapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private String name;

    @Column(name = "salary")
    private BigDecimal salary;

    @OneToMany(mappedBy = "position")
    List<Employees> employeesList;

    @ManyToMany
    @JoinTable(name = "position_department", joinColumns=@JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    List<Department> departmentList;

}

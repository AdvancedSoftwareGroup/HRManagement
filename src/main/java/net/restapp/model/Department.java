package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import javax.persistence.*;
import java.util.List;
/**
 * The class implements a set of methods for working
 * with entities of the {@link Department} class.
 */

@Entity
@Table(name = "department")
@Getter
@Setter
@EqualsAndHashCode(exclude = "positions")
public class Department {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Position> positions;


}

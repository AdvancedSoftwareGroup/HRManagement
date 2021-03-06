package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * The class implements a set of methods for working
 * with entities of the {@link Role} class.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@EqualsAndHashCode(exclude = "userList")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull(message = "This field must be NOT NULL")
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    List<User> userList;
}

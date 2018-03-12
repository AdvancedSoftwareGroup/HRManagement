package net.restapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Email(message="Please provide a valid email address")
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Employees employees;

}

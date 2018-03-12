package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.model.Employees;
import net.restapp.model.Role;

/**
 * The {@link UserReadDTO} to read a {@link net.restapp.model.User} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel

public class UserReadDTO {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String email;

    @ApiModelProperty(position = 3)
    private Role role;

}
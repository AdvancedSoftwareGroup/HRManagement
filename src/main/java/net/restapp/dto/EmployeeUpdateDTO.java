package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.mapper.Entity;
import net.restapp.model.Position;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The {@link EmployeeUpdateDTO} to update a {@link net.restapp.model.Employees} entity by Rest Controller.
 */
@Getter
@Setter
@ApiModel
public class EmployeeUpdateDTO {

    @ApiModelProperty(position = 1)
    @NotNull(message = "first name must be not null")
    private String firstName;

    @ApiModelProperty(position = 2)
    @NotNull(message = "last name must be not null")
    private String lastName;

    @Entity(Position.class)
    @NotNull(message = "position must be not null")
    @ApiModelProperty(position = 3)
    private Long positionId;

    @ApiModelProperty(position = 4)
    @NotNull(message = "experience must be not null")
    private int experience;

    @ApiModelProperty(position = 5)
    @NotNull(message = "start working date must be not null")
    private Date startWorkingDate;

}

package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * The {@link EmployeeChangeRoleDTO} to change Role for a {@link net.restapp.model.Employees} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class EmployeeChangeRoleDTO {

    @ApiModelProperty(position = 1)
    @NotNull(message = "employee Id must be not null")
    private Long employeeId;

    @ApiModelProperty(position = 2)
    @NotNull(message = "role Id must be not null")
    private Long roleId;
}

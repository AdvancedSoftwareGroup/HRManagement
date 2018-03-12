package net.restapp.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


/**
 * The {@link EmployeeReadDTO} to read a {@link net.restapp.model.Employees} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel

public class EmployeeReadDTO {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String firstName;

    @ApiModelProperty(position = 3)
    private String lastName;

    @ApiModelProperty(position = 4)
    private int availableVacationDay;

    @ApiModelProperty(position = 5)
    private int experience;

    @ApiModelProperty(position = 6)
    private Date startWorkingDate;

    @ApiModelProperty(position = 7)
    private UserReadDTO user;

    @ApiModelProperty(position = 8)
    private PositionReadDTO position;


}

package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The {@link WorkingHoursReadDTO} to read a {@link net.restapp.model.WorkingHours} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class WorkingHoursReadDTO {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Date startTime;

    @ApiModelProperty(position = 3)
    private BigDecimal salary;

    @ApiModelProperty(position = 4)
    private BigDecimal hours;

    @ApiModelProperty(position = 5)
    private StatusReadDTO status;

    @ApiModelProperty(position = 6)
    private EventReadDTO event;

    @ApiModelProperty(position = 7)
    private EmployeeReadDTO employees;
}

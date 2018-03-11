package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.mapper.Entity;
import net.restapp.model.Employees;
import net.restapp.model.Event;
import net.restapp.model.Status;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The {@link WorkingHoursCreateDTO} to create a {@link net.restapp.model.WorkingHours} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class WorkingHoursCreateDTO {


    @ApiModelProperty(position = 1)
    @NotNull(message = "startTime must be not null")
    private Date startTime;

    @ApiModelProperty(position = 2)
    @NotNull(message = "hours must be not null")
    @Min(value = 0, message = "hours must be 0 and greater")
    private BigDecimal hours;

    @Entity(Status.class)
    @ApiModelProperty(position = 3)
    @NotNull(message = "status must be not null")
    private Long statusId;

    @Entity(Event.class)
    @ApiModelProperty(position = 4)
    @NotNull(message = "event must be not null")
    private Long eventId;

    @Entity(Employees.class)
    @ApiModelProperty(position = 5)
    @NotNull(message = "Employee must be not null")
    private Long employeesId;
}

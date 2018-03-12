package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.mapper.Entity;
import net.restapp.model.Department;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * The {@link PositionCreateDTO} to create a {@link net.restapp.model.Position} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel

public class PositionCreateDTO {

    @ApiModelProperty(position = 1)
    @NotNull(message = "name must be not null")
    private String name;

    @ApiModelProperty(position = 2)
    @NotNull(message = "dayForVacation must be not null")
    @Min(value = 0, message = "days for vacation must be 0 and greater")
    private int dayForVacation;

    @ApiModelProperty(position = 3)
    @NotNull(message = "salary per hour must be not null")
    @Min(value = 0, message = "hourly rest must be 0 and greater")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "99999.00")
    private BigDecimal salary;

    @Entity(Department.class)
    @ApiModelProperty(position = 4)
    @NotNull(message = "department must be not null")
    private Long departmentId;


}

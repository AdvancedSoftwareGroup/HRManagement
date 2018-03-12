package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.model.Department;

import java.math.BigDecimal;

/**
 * The {@link PositionReadDTO} to read a {@link net.restapp.model.Position} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel

public class PositionReadDTO {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private int dayForVacation;

    @ApiModelProperty(position = 4)
    private BigDecimal salary;

    @ApiModelProperty(position = 5)
    private Department department;

}

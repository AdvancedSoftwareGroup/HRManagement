package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The {@link ArchiveSalaryReadDTO} to read a {@link net.restapp.model.ArchiveSalary} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class ArchiveSalaryReadDTO {

    @ApiModelProperty(position = 1)
    private long id;

    @ApiModelProperty(position = 2)
    private Date date;

    @ApiModelProperty(position = 3)
    private BigDecimal monthSalary;

    @ApiModelProperty(position = 4)
    private EmployeeReadDTO employee;
}

package net.restapp.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Position;
import net.restapp.model.User;
import net.restapp.model.WorkingHours;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

//    //todo: thing about thi variable
//    @ApiModelProperty(position = 8)
//    private Position position;


}

package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.mapper.Entity;
import net.restapp.model.Position;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.rest.core.annotation.Description;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The {@link EmployeeCreateDTO} to create a {@link net.restapp.model.Employees} entity by Rest Controller.
 */
@Getter
@Setter
@ApiModel
public class EmployeeCreateDTO {

    @ApiModelProperty(required = true, position = 1)
    @NotNull(message = "firstName must be not null")
    private String firstName;

    @ApiModelProperty(required = true, position = 2)
    @NotNull(message = "lastName must be not null")
    private String lastName;

    @ApiModelProperty(required = true, position = 3)
    @NotNull(message = "email must be not null")
    @Email
    private String email;

    @ApiModelProperty(required = true, position = 4)
    //("Experience it's count of month")
    @Min(value = 0, message = "experience must be 0 and greater")
    @NotNull(message = "experience must be not null")
    private int experience;

    @ApiModelProperty(required = true, position = 5)
    @NotNull(message = "startWorkingDate must be not null")
    private Date startWorkingDate;

    @Entity(Position.class)
    @ApiModelProperty(required = true, position = 6)
    @NotNull(message = "positionId must be not null")
    private Long positionId;


}

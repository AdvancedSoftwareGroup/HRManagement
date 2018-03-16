package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The {@link DepartmentCreateDTO} to create a {@link net.restapp.model.Department} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class DepartmentCreateDTO {

    private long id;
    @ApiModelProperty(position = 1)
    @Pattern(regexp = RegexpPatterns.patternStringWithNumbersLettersAndDash,
            message = RegexpPatterns.messageStringWithNumbersLettersAndDash)
    @NotNull(message = "name must be not null")
    private String name;


}

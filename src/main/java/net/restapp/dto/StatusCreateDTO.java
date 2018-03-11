package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.restapp.Validator.RegexpPatterns;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * The {@link StatusCreateDTO} to create a {@link net.restapp.model.Status} entity by Rest Controller.
 */
@Getter
@Setter
@ApiModel
public class StatusCreateDTO {

    @ApiModelProperty(position = 1)
    @NotNull(message = "This field must be NOT NULL")
    @Pattern(regexp= RegexpPatterns.patternStringWithNumbersLettersAndDash,
            message = RegexpPatterns.messageStringWithNumbersLettersAndDash)
    private String name;

    @ApiModelProperty(position = 2)
    @NotNull(message = "This field must be NOT NULL")
    @Min(value = 0, message = "salary coefficient must be 0 and greater")
    @Max(value = 2, message = "salary coefficient must be less than 2")
    private BigDecimal salary_coef;
}

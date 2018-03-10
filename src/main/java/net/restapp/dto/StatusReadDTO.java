package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * The {@link StatusReadDTO} to read a {@link net.restapp.model.Status} entity by Rest Controller.
 */
@Getter
@Setter
@ApiModel
public class StatusReadDTO {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private BigDecimal salary_coef;
}

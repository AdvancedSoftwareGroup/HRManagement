package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The {@link DepartmentReadDTO} to read a {@link net.restapp.model.Department} entity by Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class DepartmentReadDTO {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 5)
    private List<PositionReadDTO> positions;
}

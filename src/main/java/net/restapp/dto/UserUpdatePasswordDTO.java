package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The {@link UserUpdatePasswordDTO} to update password a {@link net.restapp.model.User} entity by
 * Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class UserUpdatePasswordDTO {

    @ApiModelProperty(required = true, position = 1)
    @NotNull
    private String oldPassword;

    @ApiModelProperty(required = true, position = 2)
    @NotNull(message = "password must be not null")
    @Size(min = 8, max = 25, message = "password length must be 8-25 symbols")
    private String password;

    @ApiModelProperty(required = true, position = 3)
    @NotNull(message = "confirmPassword must be not null")
    @Size(min = 8, max = 25, message = "password length must be 8-25 symbols")
    private String confirmPassword;

    @AssertTrue(message = "passwords does not matches")
    private boolean isPasswordsMatches() {
        return password.equals(confirmPassword);
    }
}

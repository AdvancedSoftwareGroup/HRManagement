package net.restapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * The {@link UserUpdateEmailDTO} to update password a {@link net.restapp.model.User} entity by
 * Rest Controller.
 */

@Getter
@Setter
@ApiModel
public class UserUpdateEmailDTO {

    @ApiModelProperty(required = true, position = 1)
    @NotNull(message = "email must be not null")
    private String old_email;

    @ApiModelProperty(required = true, position = 2)
    @NotNull(message = "email must be not null")
    @Email
    private String email;

    @ApiModelProperty(required = true, position = 3)
    @NotNull(message = "confirmEmail must be not null")
    private String confirmEmail;

    @AssertTrue(message = "emails does not matches")
    private boolean isEmailsMatches() {
        return email.equals(confirmEmail);
    }


}

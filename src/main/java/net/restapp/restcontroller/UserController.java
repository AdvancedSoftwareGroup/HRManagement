package net.restapp.restcontroller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.restapp.dto.UserReadDTO;
import net.restapp.dto.UserUpdateEmailDTO;
import net.restapp.dto.UserUpdatePasswordDTO;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.User;
import net.restapp.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/employees/user")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
@Api(value = "user", description = "Operations pertaining with login user")
public class UserController {

    @Autowired
    private DtoMapper mapper;

    @Autowired
    UserService userService;

    @ApiOperation(value = "View a login user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login user successfully showed"),
            @ApiResponse(code = 401, message = "You are not authorized to update user"),
            @ApiResponse(code = 403, message = "Accessing updating the user you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserReadDTO getUser(HttpServletRequest request) {

        Long userId = getLoginUserId(request);
        User user = userService.getById(userId);

        return mapper.simpleFieldMap(user, UserReadDTO.class);
    }

    @ApiOperation(value = "Update password for login User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update user"),
            @ApiResponse(code = 403, message = "Accessing updating the user you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @RequestMapping(value = "/pass",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setUser(@Valid @RequestBody UserUpdatePasswordDTO dto,
                                          HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        userService.updateUserPasswordById(userId,dto);
    }

    @ApiOperation(value = "Update email for login User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update user"),
            @ApiResponse(code = 403, message = "Accessing updating the user you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @RequestMapping(value = "/email",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setUser(@Valid @RequestBody UserUpdateEmailDTO dto,
                        HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        userService.updateUserEmailById(userId,dto);
    }

    private Long getLoginUserId(HttpServletRequest request){
        String userLoginEmail=request.getUserPrincipal().getName();
        User user = userService.findByEmail(userLoginEmail);
        return user.getId();
    }

}

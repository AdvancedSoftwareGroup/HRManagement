package net.restapp.restcontroller;

import net.restapp.exception.EntityNullException;
import net.restapp.model.User;
import net.restapp.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/employees/user")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> getUser(HttpServletRequest request) {

        Long userId = getLoginUserId(request);
        User user = userService.getById(userId);

        if (user == null) {
            String msg = String.format("There is no user with id: %d", userId);
            throw new EntityNotFoundException(msg);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> setUser(@RequestBody @Valid User user,
                                          HttpServletRequest request) {

        Long userId = getLoginUserId(request);

        if (user == null) {
            throw new EntityNullException("user can't be null");
        }
        user.setId(userId);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    private Long getLoginUserId(HttpServletRequest request){
        String userLoginEmail=request.getUserPrincipal().getName();
        User user = userService.findByEmail(userLoginEmail);
        return user.getId();
    }


}

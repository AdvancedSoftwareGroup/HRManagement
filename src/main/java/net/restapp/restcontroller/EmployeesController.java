package net.restapp.restcontroller;

import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.model.Employees;
import net.restapp.model.User;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    @Autowired
    UserService userService;


    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getEmployee(@PathVariable("employeeId") Long employeeId,
                                              HttpServletRequest request) {

        if (request.isUserInRole("ROLE_USER")) {
            if (!checkLoginUserHavePetitionForThisInfo(employeeId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about emploee with id=" + employeeId);
            }
        }
        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Employees employees = employeesService.getById(employeeId);

        if (employees == null) {
            String msg = String.format("There is no employee with id: %d", employeeId);
            throw new EntityNotFoundException(msg);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteEmployee(@PathVariable("employeeId") Long employeeId,
                                                 HttpServletRequest request) {

        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Employees employees = employeesService.getById(employeeId);

        if (employees == null) {
            String msg = String.format("There is no employee with id: %d", employeeId);
            throw new EntityNotFoundException(msg);
        }
        employeesService.delete(employeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editEmployee(@PathVariable("employeeId") Long employeeId,
                                               @RequestBody @Valid Employees employees) {

        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Employees employees1 = employeesService.getById(employeeId);

        if (employees1 == null) {
            String msg = String.format("There is no employee with id: %d", employeeId);
            throw new EntityNotFoundException(msg);
        }

        employees.setId(employeeId);
        employeesService.save(employees);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllEmployees() {
        List<Employees> employees = employeesService.getAll();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveEmployee(@RequestBody @Valid Employees employees,
                                               UriComponentsBuilder builder) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (employees == null) {
            throw new EntityNullException("employee can't be null");
        }
        employeesService.save(employees);

        httpHeaders.setLocation(builder.path("/employees/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(employees, httpHeaders, HttpStatus.CREATED);
    }

    private boolean checkLoginUserHavePetitionForThisInfo(Long employeeId, HttpServletRequest request) {
        String email=request.getUserPrincipal().getName();
        User user = userService.findByEmail(email);
        return user.getId() == employeeId;
    }
}

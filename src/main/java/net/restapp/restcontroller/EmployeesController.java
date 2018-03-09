package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.model.ArchiveSalary;
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
@Api(value="employee", description="Operations pertaining to employee in HRManagement")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "View employee by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the employee by id"),
            @ApiResponse(code = 403, message = "Accessing the employee by id you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employees> getEmployee(@ApiParam(value = "id of Employee", required = true) @PathVariable("employeeId") Long employeeId,
                                              HttpServletRequest request) {

        if (request.isUserInRole("ROLE_USER")) {
            if (!checkLoginUserHavePetitionForThisInfo(employeeId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about employee with id=" + employeeId);
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

    @ApiOperation(value = "Delete employee by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete employee"),
            @ApiResponse(code = 403, message = "Accessing deletion the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found")
    })

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employees> deleteEmployee(@ApiParam(value = "id of Employee", required = true) @PathVariable("employeeId") Long employeeId) {

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
    @ApiOperation(value = "Update employee by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update employee"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employees> editEmployee(@ApiParam(value = "id of Employee", required = true) @PathVariable("employeeId") Long employeeId,
                                               @ApiParam(value = "json body of Employee", required = true) @RequestBody @Valid Employees employees) {

        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Employees employees1 = employeesService.getById(employeeId);

        if (employees1 == null) {
            String msg = String.format("There is no employee with id: %d", employeeId);
            throw new EntityNotFoundException(msg);

        }

        if (employees == null) {
            throw new EntityNullException("position can't be null");
        }

        employees.setId(employeeId);
        employeesService.save(employees);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @ApiOperation(value = "Retrieve all employees", response = ArchiveSalary.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update employee"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Employees>> getAllEmployees() {
        List<Employees> employees = employeesService.getAll();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @ApiOperation(value = "Create employee", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to create employee"),
            @ApiResponse(code = 403, message = "Accessing creating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employees> saveEmployee(@ApiParam(value = "json body of Employee", required = true) @RequestBody @Valid Employees employees,
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

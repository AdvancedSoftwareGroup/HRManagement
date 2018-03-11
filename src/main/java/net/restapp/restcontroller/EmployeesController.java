package net.restapp.restcontroller;

import io.swagger.annotations.*;

import net.restapp.Utils.Email;
import net.restapp.Utils.LettersExample;
import net.restapp.dto.EmployeeCreateDTO;
import net.restapp.dto.EmployeeReadDTO;
import net.restapp.dto.EmployeeUpdateDTO;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.Employees;
import net.restapp.model.User;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Api(value="employee", description="Operations pertaining to employee")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    @Autowired
    UserService userService;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    JavaMailSender mailSender;

//-------------------------------- get ----------------------------------------

    @ApiOperation(value = "Search an employee by ID", response = EmployeeReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the employee by id"),
            @ApiResponse(code = 403, message = "Accessing the employee by id you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public EmployeeReadDTO getEmployee(@ApiParam(value = "id of Employee", required = true) @PathVariable("employeeId") Long employeeId,
                                                       HttpServletRequest request) {

        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        if (request.isUserInRole("ROLE_USER")) {
            if (!checkLoginUserHavePetitionForThisInfo(employeeId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about employee with id=" + employeeId);
            }
        }

        Employees employees = employeesService.getById(employeeId);

        if (employees == null) {
            String msg = String.format("There is no employee with id: %d", employeeId);
            throw new EntityNotFoundException(msg);
        }
        return mapper.simpleFieldMap(employees, EmployeeReadDTO.class);
    }

//--------------------------- delete ----------------------------------
    @ApiOperation(value = "Delete employee by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete employee"),
            @ApiResponse(code = 403, message = "Accessing deletion the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })

    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteEmployee(@ApiParam(value = "id of Employee", required = true) @PathVariable("employeeId") Long employeeId) {

        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }

        employeesService.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

//---------------------------edit ----------------------------------------------------
    @ApiOperation(value = "Update employee by ID", response = EmployeeReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update employee"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity editEmployee(@ApiParam(value = "id of Employee", required = true) @PathVariable("employeeId") Long employeeId,
                                               @ApiParam(value = "json body of Employee", required = true) @NotNull @RequestBody @Valid EmployeeUpdateDTO dto) {

        if (employeeId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        if (!employeesService.isEmployeeExist(employeeId)) {
            String msg = String.format("There is no employee with id: %d", employeeId);
            throw new EntityNotFoundException(msg);
        }

        Employees employee = mapper.map(dto, Employees.class);
        employee.setId(employeeId);
        employeesService.save(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

////------------------------------------- getAll --------------------------------------------------------------
    @ApiOperation(value = "View a list of all employees", response = EmployeeReadDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully shows"),
            @ApiResponse(code = 401, message = "You are not authorized to update employee"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<EmployeeReadDTO> getAllEmployees() {
        List<Employees> employees = employeesService.getAll();
        if (employees.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return mapper.listSimpleFieldMap(employees, EmployeeReadDTO.class);

    }

////-------------------------------add --------------------------------------------------------
    @ApiOperation(value = "Create employee", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to create employee"),
            @ApiResponse(code = 403, message = "Accessing creating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity saveEmployee(
            @ApiParam(value = "json body of Employee", required = true) @RequestBody @Valid EmployeeCreateDTO dto,
            HttpServletRequest request) {

        Employees employees = mapper.map(dto, Employees.class);
        User user = new User();
        user.setEmail(dto.getEmail());
        employees.setUser(user);
        employeesService.save(employees);

        sendWelcomeLetter(user.getEmail(),request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
////-----------------------------------------------------------------------------------------

    private void sendWelcomeLetter(String email,HttpServletRequest request) {
        String domain=request.getRequestURL().toString();
        String url = domain.substring(0,domain.indexOf("api")+3);

        Email email1Send = new Email();
        LettersExample lettersExample = new LettersExample();

        email1Send.sendEmail(mailSender,email, lettersExample.createWelcomeMessage(url));
    }

    private boolean checkLoginUserHavePetitionForThisInfo(Long employeeId, HttpServletRequest request) {
        String email=request.getUserPrincipal().getName();
        User user = userService.findByEmail(email);
        return user.getId() == employeeId;
    }
}

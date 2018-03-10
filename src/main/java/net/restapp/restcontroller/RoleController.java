package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.dto.EmployeeChangeRoleDTO;
import net.restapp.dto.EmployeeReadDTO;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.Employees;
import net.restapp.servise.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees/role")
@Api(value = "employee", description = "Operations pertaining to role of employee")
@Secured("ROLE_ADMIN")
public class RoleController {

    @Autowired
    EmployeesService employeesService;

    @Autowired
    private DtoMapper mapper;

//-------------------------------getAll with role ------------------------------------------------

    @ApiOperation(value = "View all employees by role ID", response = EmployeeReadDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully got"),
            @ApiResponse(code = 401, message = "You are not authorized to get employees"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @RequestMapping(value = "/{roleId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<EmployeeReadDTO> getAllEmployees(@PathVariable("roleId") Long roleId) {

        List<Employees> employees = employeesService.getAllByRoleId(roleId);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return mapper.listSimpleFieldMap(employees, EmployeeReadDTO.class);

    }

//--------------------------------change role --------------------------------------------------------------
    @ApiOperation(value = "View all employees by role ID", response = Employees.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully got"),
            @ApiResponse(code = 401, message = "You are not authorized to get employees"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public void changeRoleForEmployee(@RequestBody @Valid EmployeeChangeRoleDTO dto) throws Exception {

         employeesService.updateEmployeeRole(dto);
    }

}

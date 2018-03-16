package net.restapp.restcontroller;
import io.swagger.annotations.*;
import net.restapp.dto.ArchiveSalaryReadDTO;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.servise.ArchiveSalaryService;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salary")
@Api(value="archive", description="Operations pertaining to archive")
public class ArchiveSalaryController {

    @Autowired
    ArchiveSalaryService repoArchiveSalary;
    @Autowired
    UserService userService;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    DtoMapper mapper;

//---------------------------------get salary for period for employee with id --------------------------------------------
    @ApiOperation(value = "View a list of salary from archive by employee id and start/end date", response = ArchiveSalaryReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view the salary for period"),
            @ApiResponse(code = 403, message = "Accessing the salary for period you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The salary for period you were trying to reach is not found")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/{startDate}/{endDate}/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ArchiveSalaryReadDTO> getSalaries(
            @ApiParam(value = "start Date of salary accrual date", required = true) @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @ApiParam(value = "end Date of salary accrual date", required = true) @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @ApiParam(value = "ID of employee", required = true) @PathVariable("employeeId") Long employeeId,
            HttpServletRequest request) {

        if (startDate == null || endDate == null || employeeId == null) {
            String msg = "startDate, endDate, employeeId must not be null ";
            throw new PathVariableNullException(msg);
        }

        Employees employees = employeesService.getById(employeeId);
        if (employees == null) {
            String msg = "employee not found";
            throw new EntityNotFoundException(msg);
        }
        if (request.isUserInRole("ROLE_USER")) {
            if (userService.checkLoginUserHavePetitionForThisInfo(employeeId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about employee with id=" + employeeId);
            }
        }
        List<ArchiveSalary> archiveSalaries = repoArchiveSalary.findDateBetween(startDate, endDate, employees);

        if (archiveSalaries.isEmpty()) {
            String msg = "ArchiveSalary not found";
            throw new EntityNotFoundException(msg);
        }

        return mapper.listSimpleFieldMap(archiveSalaries,ArchiveSalaryReadDTO.class);
    }
//----------------------------------get via date for employeeId ---------------------------------------
    @ApiOperation(value = "View salary from archive via Date", response = ArchiveSalaryReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view the salary via date"),
            @ApiResponse(code = 403, message = "Accessing the salary via Date you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The salary via Date you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/{salaryViaDate}/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ArchiveSalaryReadDTO getSalary(
            @ApiParam(value = "Day of Salary", required = true) @PathVariable("salaryViaDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date salaryViaDate,
            @ApiParam(value = "ID of employee", required = true) @PathVariable("employeeId") Long employeeId,
            HttpServletRequest request) {
        if (salaryViaDate == null) {
            String msg = "salaruViaDate must not be null ";
            throw new PathVariableNullException(msg);
        }

        Employees employee = employeesService.getById(employeeId);
        if (employee == null) {
            String msg = "employee not found";
            throw new EntityNotFoundException(msg);
        }

        if (request.isUserInRole("ROLE_USER")) {
            if (userService.checkLoginUserHavePetitionForThisInfo(employeeId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about employee with id=" + employeeId);
            }
        }

        ArchiveSalary archiveSalary = repoArchiveSalary.findSalaryViaDate(salaryViaDate, employee);

        if (archiveSalary == null) {
            String msg = "Archive Salary not found";
            throw new EntityNotFoundException(msg);
        }

        return mapper.simpleFieldMap(archiveSalary,ArchiveSalaryReadDTO.class);
    }
//------------------------------------------getAll -------------------------------------------------
    @ApiOperation(value = "View list of all salary entries from archive", response = ArchiveSalaryReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of salary entries"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of salary entries"),
            @ApiResponse(code = 403, message = "Accessing the list of salary entries you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The salary entries not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ArchiveSalaryReadDTO> getAllSalaries(){
        List<ArchiveSalary> archiveSalaries = repoArchiveSalary.getAll();
        if (archiveSalaries.isEmpty()) {
            String msg = "Archive Salary not found";
            throw new EntityNotFoundException(msg);
        }
        return mapper.listSimpleFieldMap(archiveSalaries,ArchiveSalaryReadDTO.class);
    }

//--------------------------------get --------------------------------------------
    @ApiOperation(value = "View entry of salary from archive by id", response = ArchiveSalaryReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved entry of salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view entry of salary"),
            @ApiResponse(code = 403, message = "Accessing the entry of salary you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The entry of salary you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{archiveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ArchiveSalaryReadDTO getDepartment(@ApiParam(value = "ID of salary entry in archive", required = true) @PathVariable("archiveId") Long archiveId){

        if (archiveId == null){
            String msg = "archive Id must not be null ";
            throw new PathVariableNullException(msg);
        }
        ArchiveSalary archiveSalary =  repoArchiveSalary.getById(archiveId);

        if (archiveSalary == null) {
            String msg = "workingHours not found";
            throw new EntityNotFoundException(msg);
        }
        return mapper.simpleFieldMap(archiveSalary,ArchiveSalaryReadDTO.class);
    }



}
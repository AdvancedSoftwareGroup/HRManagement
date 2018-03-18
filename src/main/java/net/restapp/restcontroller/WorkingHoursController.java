package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.Utils.LettersExample;
import net.restapp.Utils.PDF;
import net.restapp.dto.EmployeeReadDTO;
import net.restapp.dto.WorkingHoursCreateDTO;
import net.restapp.dto.WorkingHoursReadDTO;
import net.restapp.exception.EntityConstraintException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.EmployeeSheet;
import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.servise.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/workingHours")
@Api(value = "workingHours", description = "Operations pertaining to workingHours")
public class WorkingHoursController {


    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    DtoMapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    EmployeesService employeesService;

////----------------------------get List<Employees> that available for this data-----------------------------
    @ApiOperation(value = "get List of free employees for this date", response = EmployeeReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of employees"),
            @ApiResponse(code = 401, message = "You are not authorized to view the list of employees"),
            @ApiResponse(code = 403, message = "Accessing the list of employees you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The list of employees you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/getAvailableByDate/{date}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<EmployeeReadDTO> getAvailableEmployees(
    @ApiParam(value = "start Date of salary accrual date", required = true) @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        if (date == null) {
            String msg = "date must not be null ";
            throw new PathVariableNullException(msg);
        }

        List<Employees> availableEmployees = workingHoursService.getAvailableEmployeesForDate(date);

        return mapper.listSimpleFieldMap(availableEmployees,EmployeeReadDTO.class);
    }

//    //Принимает id работника
//    //Возвращяет доступное количество дней отдыха НА ДАННЫЙ МОМЕНТ
//////----------------------------get available vacation days for employee-----------------------------
    @ApiOperation(value = "get the count available vacantion day for this employees", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/getAvailableVacationDay/{workingHoursId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAvailableVacationDay(
            @ApiParam(value = "id of employee to get vacantion day cout", required = true) @PathVariable("workingHoursId") Long employeesId,
            HttpServletRequest request) {

        if (employeesId == null) {
            String msg = "working Hours Id must be not null ";
            throw new PathVariableNullException(msg);
        }
        if (request.isUserInRole("ROLE_USER")) {
            if (userService.checkLoginUserHavePetitionForThisInfo(employeesId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about employee with id=" + employeesId);
            }
        }
        Integer availableVacationDay =  employeesService.getAvailableVacationDay(employeesId);

        return new ResponseEntity<>(availableVacationDay, HttpStatus.OK);
    }


////---------------------------get --------------------------------
    @ApiOperation(value = "get workingHours by id", response = WorkingHoursReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public WorkingHoursReadDTO getWorkingHours(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long workingHoursId) {

        if (workingHoursId == null) {
            String msg = "workingHours Id must not be null ";
            throw new PathVariableNullException(msg);
        }

        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours == null) {
            String msg = "workingHours not found";
            throw new EntityNotFoundException(msg);
        }
        return  mapper.simpleFieldMap(workingHours,WorkingHoursReadDTO.class);
    }

//---------------------------delete -----------------------------------------------------
   @ApiOperation(value = "delete workingHours by id",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of workingHours"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about workingHours"),
            @ApiResponse(code = 403, message = "Accessing of workingHours you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The WorkingHours you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "wrong arguments")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteWorkingHoursI(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long workingHoursId) {

        if (workingHoursId == null) {
            String msg = "working Hours Id must not be null ";
            throw new PathVariableNullException(msg);
        }
        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours == null) {
            String msg = "workingHours not found";
            throw new EntityNotFoundException(msg);
        }

        workingHoursService.delete(workingHoursId);

        if (workingHoursService.getById(workingHoursId) != null) {
            String msg = "can't delete";
            throw new EntityConstraintException(msg);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
////--------------------------------edit --------------------------------
    @ApiOperation(value = "edit workingHours by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of workingHours"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about workingHours"),
            @ApiResponse(code = 403, message = "Accessing of workingHours you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The workingHours you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity editWorkingHours(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long workingHoursId,
            @ApiParam(value = "json body of WorkingHours", required = true) @RequestBody @Valid WorkingHoursCreateDTO dto) {


        if (workingHoursId == null) {
            String msg = "working Hours Id must not be null ";
            throw new PathVariableNullException(msg);
        }
        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours == null) {
            String msg = "workingHours not found";
            throw new EntityNotFoundException(msg);
        }
        WorkingHours workingHours2 = mapper.map(dto,WorkingHours.class);
        workingHours2.setId(workingHoursId);
        workingHoursService.save(workingHours2);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

////---------------------------getAll ---------------------------------------------------
    @ApiOperation(value = "get all employee", response = WorkingHoursReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WorkingHoursReadDTO> getAllWorkingHours() {
        List<WorkingHours> workingHours = workingHoursService.getAll();
        if (workingHours.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return mapper.listSimpleFieldMap(workingHours,WorkingHoursReadDTO.class);
    }


    //---------------------------------add --------------------------------
    @ApiOperation(value = "Add working hour", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Working hour successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to add working hour"),
            @ApiResponse(code = 403, message = "Accessing adding working hour you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity saveWorkingHours(
            @ApiParam(value = "json body of WorkingHours", required = true) @RequestBody @Valid WorkingHoursCreateDTO dto) {

        WorkingHours workingHours = mapper.map(dto,WorkingHours.class);
        workingHoursService.save(workingHours);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

////----------------------------------getAll for employee---------------------------------------------------
    @ApiOperation(value = "get all working hours for employee", response = WorkingHoursReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_USER"})
    @RequestMapping(value = "/getAll/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WorkingHoursReadDTO> getAllWorkingHoursForEmployee(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long employeeId,
            HttpServletRequest request) {

        if (employeeId == null) {
            String msg = "working Hours Id must be not null ";
            throw new PathVariableNullException(msg);
        }

        if (request.isUserInRole("ROLE_USER")) {
            if (userService.checkLoginUserHavePetitionForThisInfo(employeeId, request)) {
                throw new AccessDeniedException("You don't have permit to get iformation about employee with id=" + employeeId);
            }
        }

        List<WorkingHours> workingHours = workingHoursService.getAllWithEmployeeId(employeeId);
        if (workingHours.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return mapper.listSimpleFieldMap(workingHours,WorkingHoursReadDTO.class);
    }


}

package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.exception.PathVariableNullException;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.model.WorkingHours;
import net.restapp.servise.EmployeesService;
import net.restapp.servise.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/workingHours")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value = "workingHours", description = "Operations pertaining to workingHours in HRManagement")
public class WorkingHoursController {


    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    EmployeesService employeesService;

    // Принамает дату создания события
    // Возвращяет List<Employees> со всеми свободными сотрудника на данную дату
    @ApiOperation(value = "get List of free employees for this date", response = Employees.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of employees"),
            @ApiResponse(code = 401, message = "You are not authorized to view the list of employees"),
            @ApiResponse(code = 403, message = "Accessing the list of employees you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The list of employees you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/getAvailableByDate/{date}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Employees>> getAvailableEmployees(
    @ApiParam(value = "start Date of salary accrual date", required = true) @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        if (date == null) {
            String msg = "date must not be null ";
            throw new PathVariableNullException(msg);
        }

        List<Employees> allEmployeesList = employeesService.getAll();
        List<Employees> notAvailableEmployeesList = workingHoursService.findAllEmployeeForDate(date);
        List<Employees> availableEmployees = new ArrayList<>();

        for (int i = 0; i < allEmployeesList.size(); i++) {
            for (int j = 0; j < notAvailableEmployeesList.size(); j++) {

                if (allEmployeesList.get(i) != notAvailableEmployeesList.get(j)) {
                    availableEmployees.add(allEmployeesList.get(i));
                }
            }
        }

        return new ResponseEntity<>(availableEmployees, HttpStatus.OK);
    }

    //Принимает id работника
    //Возвращяет доступное количество дней отдыха НА ДАННЫЙ МОМЕНТ
    @ApiOperation(value = "get the count available vacantion day for this employees", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/getAvailableVacationDay/{workingHoursId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAvailableVacationDay(
            @ApiParam(value = "id of employee to get vacantion day cout", required = true) @PathVariable("workingHoursId") Long employeesId) {

        if (employeesId == null) {

            String msg = "working Hours Id must be not null ";
            throw new PathVariableNullException(msg);
        }

        Integer availableVacationDay = workingHoursService.getAvailableVacationDay(employeesId);

        return new ResponseEntity<>(availableVacationDay, HttpStatus.OK);
    }


    //GET BY ID
    @ApiOperation(value = "get employee by id", response = Employees.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getWorkingHours(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long workingHoursId) {

        if (workingHoursId == null) {
            String msg = "working Hours Id must not be null ";
            throw new PathVariableNullException(msg);
        }

        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours == null) {
            String msg = "workingHours not found";
            throw new PathVariableNullException(msg);
        }
        return new ResponseEntity<>(workingHours, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(value = "delete employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteWorkingHoursI(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long workingHoursId
    ) {

        if (workingHoursId == null) {
            String msg = "working Hours Id must not be null ";
            throw new PathVariableNullException(msg);
        }
        WorkingHours workingHours = workingHoursService.getById(workingHoursId);

        if (workingHours == null) {
            String msg = "workingHours not found";
            throw new PathVariableNullException(msg);
        }

        workingHoursService.delete(workingHoursId);

        if (workingHoursService.getById(workingHoursId) != null) {
            String msg = "can't delete";
            throw new PathVariableNullException(msg);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //EDIT
    @ApiOperation(value = "edit employee by id", response = Employees.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{workingHoursId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editWorkingHours(
            @ApiParam(value = "id of workingHour", required = true) @PathVariable Long workingHoursId,
            @ApiParam(value = "json body of WorkingHours", required = true) @RequestBody @Valid WorkingHours workingHours
    ) {

        if (workingHoursId == null) {
            String msg = "workingHours not found";
            throw new PathVariableNullException(msg);
        }

        WorkingHours workingHours2 = workingHoursService.getById(workingHoursId);

        if (workingHours2 == null) {
            throw new EntityNotFoundException();
        }
        workingHours.setId(workingHoursId);
        workingHoursService.save(workingHours);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //GET ALL
    @ApiOperation(value = "get all employee", response = Employees.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved of employee"),
            @ApiResponse(code = 401, message = "You are not authorized to view the information about employee"),
            @ApiResponse(code = 403, message = "Accessing of employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllWorkingHours(HttpServletRequest request) {
        List<WorkingHours> workingHours = workingHoursService.getAll();
        if (workingHours.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(workingHours, HttpStatus.OK);
    }


    //ADD
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveWorkingHours(
            @ApiParam(value = "json body of Departament", required = true) @RequestBody @Valid WorkingHours workingHours,
            UriComponentsBuilder builder) {

        HttpHeaders httpHeaders = new HttpHeaders();

        if (workingHours.getStartTime() == null) {

            String msg = "Start time at working hours must not to be null";
            throw new PathVariableNullException(msg);
        }

        if (workingHours.getEmployees() == null) {
            String msg = "Employees at working hours must not to be null";
            throw new PathVariableNullException(msg);
        }
        if (workingHours.getEvent() == null) {


            String msg = "Event at working hours must not to be null";
            throw new PathVariableNullException(msg);

        }
        if (workingHours.getHours() == null) {

            String msg = "Hours at working hours must not to be null";
            throw new PathVariableNullException(msg);
        }
        if (workingHours.getStatus() == null) {
            String msg = "Status at working hours must not to be null";
            throw new PathVariableNullException(msg);
        }

        BigDecimal salaryPerHour = workingHours.getEmployees().getPosition().getSalary();
        BigDecimal statusCoef = workingHours.getStatus().getSalary_coef();
        BigDecimal hours = workingHours.getHours();

        BigDecimal salary = hours.multiply(salaryPerHour.multiply(statusCoef));
        workingHours.setSalary(salary);
        workingHoursService.save(workingHours);

        httpHeaders.setLocation(builder.path("/workingHours/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(workingHours, httpHeaders, HttpStatus.CREATED);
    }

}

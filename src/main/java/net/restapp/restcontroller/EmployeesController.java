package net.restapp.restcontroller;

import net.restapp.model.Employees;
import net.restapp.servise.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    MyResponseRequest myResponseRequest = new MyResponseRequest(new Employees());

    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getEmployee(@PathVariable("employeeId") Long employeeId,
                                                HttpServletRequest request){

        if (employeeId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "employee id must be not null");
        }
        Employees employees =  employeesService.getById(employeeId);

        if (employees == null) {
            return myResponseRequest.notFoundRequest(request,employeeId);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteEmployee(@PathVariable("employeeId") Long employeeId,
                                                   HttpServletRequest request){

        if (employeeId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "employee id must be not null");
        }
        Employees employees = employeesService.getById(employeeId);

        if (employees == null) {
            return myResponseRequest.notFoundRequest(request,employeeId);
        }
        employeesService.delete(employeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{employeeId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editEmployee(@PathVariable("employeeId") Long employeeId,
                                                 @RequestBody @Valid Employees employees,
                                                 HttpServletRequest request){

        if (employeeId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "employee id must be not null");
        }
        Employees employees1 = employeesService.getById(employeeId);

        if (employees1 == null) {
            return myResponseRequest.notFoundRequest(request,employeeId);
        }
        employees.setId(employeeId);
        employeesService.edit(employees);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllEmployees(HttpServletRequest request){
        List<Employees> employees = employeesService.getAll();
        if (employees.isEmpty()) {
            return myResponseRequest.notFoundRequest(request,null);
        }
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveEmployee(@RequestBody @Valid Employees employees,
                                                 UriComponentsBuilder builder,
                                                 HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (employees == null){
            return myResponseRequest.bedRequest(
                    request,
                    "employee id must be not null");
        }
        employeesService.add(employees);

        httpHeaders.setLocation(builder.path("/employees/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(employees, httpHeaders, HttpStatus.CREATED);
    }


}

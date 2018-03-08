package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Department;
import net.restapp.servise.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value="department", description="Operations pertaining to department in HRManagement")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value = "View departament by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the department by id"),
            @ApiResponse(code = 403, message = "Accessing the department by id you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The department you were trying to reach is not found")
    })
    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getDepartment(@ApiParam(value = "id of Departament", required = true) @PathVariable Long departmentId){

        if (departmentId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Department department =  departmentService.getById(departmentId);

        if (department == null) {
            String msg = String.format("There is no departments with id: %d", departmentId);
            throw new EntityNotFoundException(msg);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete department by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete department"),
            @ApiResponse(code = 403, message = "Accessing deletion the department you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The department you were trying to reach is not found")
    })
    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteDepartment(@ApiParam(value = "id of Departament", required = true) @PathVariable Long departmentId){

        if (departmentId == null){
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Department department = departmentService.getById(departmentId);

        if (department == null) {
            throw new EntityNotFoundException();
        }
        departmentService.delete(departmentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @ApiOperation(value = "Update department by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update department"),
            @ApiResponse(code = 403, message = "Accessing updating the department you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The department you were trying to reach is not found")
    })
    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editDepartment(@ApiParam(value = "id of Departament", required = true) @PathVariable Long departmentId,
                                                 @ApiParam(value = "json body of Departament", required = true) @RequestBody @Valid Department department){

        if (departmentId == null){
            String msg = "PathVariable can't be null";
            throw new PathVariableNullException(msg);
        }

        Department department2 = departmentService.getById(departmentId);

        if (department2 == null) {
            throw new EntityNotFoundException();
        }
        department.setId(departmentId);
        departmentService.save(department);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @ApiOperation(value = "View a list of departments", response = ArchiveSalary.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of existing departments"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of existing departments"),
            @ApiResponse(code = 403, message = "Accessing to view list of existing departments you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The departments entries not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllDepartment(){

        List<Department> departments = departmentService.getAll();
        if (departments.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }

    @ApiOperation(value = "add departament to database", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create entry of salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view the salary for period"),
            @ApiResponse(code = 403, message = "Accessing the salary for period you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveDepartment(@ApiParam(value = "json body of Departament", required = true) @RequestBody @Valid Department department,
                                                 UriComponentsBuilder builder){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (department == null) {
            throw new EntityNullException("department can't be null");
        }
        departmentService.save(department);

        httpHeaders.setLocation(builder.path("/department/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(department,httpHeaders,HttpStatus.CREATED);
    }


}





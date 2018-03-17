package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.dto.DepartmentCreateDTO;
import net.restapp.dto.DepartmentReadDTO;
import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Department;
import net.restapp.servise.IService;
import net.restapp.servise.impl.DepartmentServiceImpl;
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
@RequestMapping(value = "/department", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value="department", description="Operations pertaining to department")
@ApiResponses(value = {
        @ApiResponse(code = 404, message = "The department you were trying to reach is not found"),
        @ApiResponse(code = 400, message = "Wrong arguments")
})
public class DepartmentController {

    @Autowired
    IService<Department> departmentService;

    @Autowired
    DtoMapper mapper;

//-------------------------------get ---------------------------------

    @ApiOperation(value = "View department by ID", response = DepartmentReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved department"),
            @ApiResponse(code = 401, message = "You are not authorized to view the department by id"),
            @ApiResponse(code = 403, message = "Accessing the department by id you were trying to reach is forbidden")
    })
    @GetMapping(value = "/{departmentId}")

    public DepartmentReadDTO getDepartment(@ApiParam(value = "id of Departament", required = true) @PathVariable Long departmentId){

        Department department =  departmentService.getById(departmentId);

        if (department == null) {
            String msg = String.format("There is no departments with id: %d", departmentId);
            throw new EntityNotFoundException(msg);
        }
        return mapper.simpleFieldMap(department,DepartmentReadDTO.class);

    }
//------------------------delete ---------------------------------
    @ApiOperation(value = "Delete department by ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete department"),
            @ApiResponse(code = 403, message = "Accessing deletion the department you were trying to reach is forbidden")

    })
    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteDepartment(@ApiParam(value = "id of Department", required = true) @PathVariable Long departmentId){

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
//---------------------------edit --------------------------------

    @ApiOperation(value = "Update department by ID", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Department successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update department"),
            @ApiResponse(code = 403, message = "Accessing updating the department you were trying to reach is forbidden")


    })
    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editDepartment(@ApiParam(value = "id of Department", required = true) @PathVariable Long departmentId,
                                                 @ApiParam(value = "json body of Department", required = true) @RequestBody @Valid DepartmentCreateDTO dto){

        if (departmentId == null){
            String msg = "PathVariable can't be null";
            throw new PathVariableNullException(msg);
        }

        Department department = departmentService.getById(departmentId);

        if (department == null) {
            throw new EntityNotFoundException();
        }
        Department department2 = mapper.map(dto,Department.class);
        department2.setId(departmentId);
        departmentService.save(department2);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//---------------------------getAll --------------------------------
    @ApiOperation(value = "View a list of departments", response = DepartmentReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of existing departments"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of existing departments"),
            @ApiResponse(code = 403, message = "Accessing to view list of existing departments you were trying to reach is forbidden")


    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<DepartmentReadDTO> getAllDepartment(){

        List<Department> departments = departmentService.getAll();
        if (departments.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return  mapper.listSimpleFieldMap(departments,DepartmentReadDTO.class);

    }
//--------------------------add ---------------------------------------

    @ApiOperation(value = "add departament to database", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create entry of salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view the salary for period"),
            @ApiResponse(code = 403, message = "Accessing the salary for period you were trying to reach is forbidden"),

    })
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity saveDepartment(
            @ApiParam(value = "json body of Department", required = true) @RequestBody @Valid DepartmentCreateDTO dto){

        if (dto == null) {
            throw new EntityNullException("department can't be null");
        }
        Department department = mapper.map(dto,Department.class);
        departmentService.save(department);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}





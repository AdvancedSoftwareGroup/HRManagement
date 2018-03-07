package net.restapp.restcontroller;

import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
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
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getDepartment(@PathVariable Long departmentId){

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


    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long departmentId){

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

    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editDepartment(@PathVariable Long departmentId,
                                                 @RequestBody @Valid Department department){

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


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveDepartment(@RequestBody @Valid Department department,
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





package net.restapp.restcontroller;

import net.restapp.model.Department;
import net.restapp.servise.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Department> getDepartment(@PathVariable("departmentId") Long departmentId){
        if (departmentId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Department department =  departmentService.getById(departmentId);

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Department> deleteDepartment(@PathVariable("departmentId") Long departmentId){
        if (departmentId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Department department = departmentService.getById(departmentId);
        if (department == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentService.delete(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Department>> getAllDepartment(){
        List<Department> departments = departmentService.getAll();
        if (departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Department> saveDepartment(@RequestBody @Valid Department department, UriComponentsBuilder builder){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (department == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        departmentService.save(department);

        httpHeaders.setLocation(builder.path("/department/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(department,httpHeaders,HttpStatus.CREATED);
    }


}
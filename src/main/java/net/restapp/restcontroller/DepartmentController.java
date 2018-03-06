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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    MyResponseRequest myResponseRequest = new MyResponseRequest(new Department());

    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getDepartment(@PathVariable("departmentId") Long departmentId,
                                                HttpServletRequest request){

        if (departmentId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "department id must be not null");
        }
        Department department =  departmentService.getById(departmentId);

        if (department == null) {
            return myResponseRequest.notFoundRequest(request,departmentId);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }




    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteDepartment(@PathVariable("departmentId") Long departmentId,
                                                   HttpServletRequest request){

        if (departmentId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "department id must be not null");
        }
        Department department = departmentService.getById(departmentId);

        if (department == null) {
            return myResponseRequest.notFoundRequest(request,departmentId);
        }
        departmentService.delete(departmentId);
        if (departmentService.getById(departmentId) != null){
            return myResponseRequest.bedRequest(
                    request,
                    "can't delete position. First you must delete employee");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{departmentId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editDepartment(@PathVariable("departmentId") Long departmentId,
                                                 @RequestBody @Valid Department department,
                                                 HttpServletRequest request){

        if (departmentId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "department id must be not null");
        }
        Department department2 = departmentService.getById(departmentId);

        if (department2 == null) {
            return myResponseRequest.notFoundRequest(request,departmentId);
        }
        department.setId(departmentId);
        departmentService.save(department);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllDepartment(HttpServletRequest request){
        List<Department> departments = departmentService.getAll();
        if (departments.isEmpty()) {
           return myResponseRequest.notFoundRequest(request,null);
        }
        return new ResponseEntity<>(departments,HttpStatus.OK);
    }


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveDepartment(@RequestBody @Valid Department department,
                                                 UriComponentsBuilder builder,
                                                 HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (department == null){
            return myResponseRequest.bedRequest(
                    request,
                    "department id must be not null");
        }
        departmentService.save(department);

        httpHeaders.setLocation(builder.path("/department/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(department,httpHeaders,HttpStatus.CREATED);
    }


}





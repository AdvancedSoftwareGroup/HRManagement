package net.restapp.restcontroller;

import net.restapp.model.Role;
import net.restapp.servise.RoleService;
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
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Role> getRole(@PathVariable("roleId") Long roleId) {
        if (roleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Role role = this.roleService.getById(roleId);

        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(role, HttpStatus.OK);

    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Role> delete(@PathVariable("roleId") long roleId) {
        Role role = roleService.getById(roleId);

        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "gatAll", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Role>> getAll() {
        List<Role> roles = roleService.getAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }


    @RequestMapping(value = "add",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Role> saveRole(@RequestBody @Valid Role role, UriComponentsBuilder builder){

        HttpHeaders httpHeaders = new HttpHeaders();

        if(role==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.roleService.save(role);
        httpHeaders.setLocation(builder.path("api/role/getAll").
                buildAndExpand().toUri());
        return new ResponseEntity<>(role,HttpStatus.CREATED);
    }

}

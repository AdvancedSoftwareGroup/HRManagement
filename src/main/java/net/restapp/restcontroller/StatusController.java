package net.restapp.restcontroller;

import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.model.Status;
import net.restapp.servise.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping(value = "/status")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
public class StatusController {

    @Autowired
    private StatusService statusService;


    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getStatus(@PathVariable("statusId") Long statusId) {

        if (statusId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }

        Status status = statusService.getById(statusId);
        if (status == null) {
            String msg = String.format("There is no position with id: %d", statusId);
            throw new EntityNotFoundException(msg);
        }

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    //------------------- Delete a Status

    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteStatus(@PathVariable("statusId") Long statusId) throws Exception {
        if (statusId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Status status = statusService.getById(statusId);

        if (status == null) {
            String msg = String.format("There is no position with id: %d", statusId);
            throw new EntityNotFoundException(msg);
        }
        statusService.delete(statusId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editStatus(@PathVariable("statusId") Long statusId,
                                                 @RequestBody @Valid Status status) throws Exception {

        if (statusId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Status status1 = statusService.getById(statusId);

        if (status1 == null) {
            String msg = String.format("There is no position with id: %d", statusId);
            throw new EntityNotFoundException(msg);
        }
        if (status == null) {
            throw new EntityNullException("status can't be null");
        }
        status.setId(statusId);
        statusService.save(status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllStatus(){
        List<Status> statusList = statusService.getAll();
        if (statusList.isEmpty()) {
            String msg = "There is no status in database ";
            throw new EntityNotFoundException(msg);
        }
        return new ResponseEntity<>(statusList,HttpStatus.OK);
    }


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveStatus(@RequestBody @Valid Status status,
                                                 UriComponentsBuilder builder) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (status == null) {
            throw new EntityNullException("status can't be null");
        }
        statusService.save(status);

        httpHeaders.setLocation(builder.path("/status/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(status,httpHeaders,HttpStatus.CREATED);
    }


}

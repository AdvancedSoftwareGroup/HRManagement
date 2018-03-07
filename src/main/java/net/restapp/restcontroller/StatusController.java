package net.restapp.restcontroller;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/status")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
public class StatusController {

    @Autowired
    private StatusService statusService;

    private MyResponseRequest myResponseRequest = new MyResponseRequest(new Status());

    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getStatus(@PathVariable("statusId") Long statusId,
                                            HttpServletRequest request) {

        if (statusId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "status id must be not null");
        }

        Status status = statusService.getById(statusId);
        if (status == null) {
            return myResponseRequest.notFoundRequest(request,statusId);
        }

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    //------------------- Delete a Status

    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteStatus(@PathVariable("statusId") Long statusId,
                                                HttpServletRequest request) throws Exception {
        if ( statusId== null){
            return myResponseRequest.bedRequest(
                    request,
                    "status id must be not null");
        }
        Status status = statusService.getById(statusId);

        if (status == null) {
            return myResponseRequest.notFoundRequest(request,statusId);
        }
        statusService.delete(statusId);
        if (statusService.getById(statusId) != null){
            return myResponseRequest.bedRequest(
                    request,
                    "can't delete status. First you must delete employee");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editStatus(@PathVariable("statusId") Long statusId,
                                                 @RequestBody @Valid Status status,
                                                 HttpServletRequest request) throws Exception {

        if (statusId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "status id must be not null");
        }
        Status status1 = statusService.getById(statusId);

        if (status1 == null) {
            return myResponseRequest.notFoundRequest(request,statusId);
        }
        status.setId(statusId);
        statusService.save(status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllStatus(HttpServletRequest request){
        List<Status> statusList = statusService.getAll();
        if (statusList.isEmpty()) {
            return myResponseRequest.notFoundRequest(request,null);
        }
        return new ResponseEntity<>(statusList,HttpStatus.OK);
    }


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveStatus(@RequestBody @Valid Status status,
                                                 UriComponentsBuilder builder,
                                                 HttpServletRequest request) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (status == null){
            return myResponseRequest.bedRequest(
                    request,
                    "status id must be not null");
        }
        statusService.save(status);

        httpHeaders.setLocation(builder.path("/status/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(status,httpHeaders,HttpStatus.CREATED);
    }


}

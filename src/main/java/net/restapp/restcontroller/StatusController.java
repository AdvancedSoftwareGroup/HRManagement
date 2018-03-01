package net.restapp.restcontroller;
import net.restapp.model.Status;
import net.restapp.servise.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/admin/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    //------------------Create a Status

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> createStatus(@RequestBody Status status, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Status " + status.getName());

        if (statusService.isStatusExist(status)) {
            System.out.println("A Status with name " + status.getName() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        statusService.save(status);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(status.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Delete a Status

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Status> deleteStatuse(@PathVariable("id") long id) {
        System.out.println("Deleting Status with id " + id);

        Status status = statusService.getById(id);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //----------------------------- Status validation
    private Map<String, String> checkStatus(Status status){
        Map<String, String> messages = new HashMap<String, String>();
        String name = status.getName();
        if (name == null || name.trim().isEmpty()){
            messages.put("name","null or empty");
        }
        return messages;
    }

    //-------------------Retrieve All Statuses

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = this.statusService.getAll();

        if (statuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    //-------------------Retrieve Single Status

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Status> getStatus(@PathVariable("id") long id) {
        Status status = statusService.getById(id);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}

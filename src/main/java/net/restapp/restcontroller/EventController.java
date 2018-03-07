package net.restapp.restcontroller;

import net.restapp.model.Event;
import net.restapp.servise.EventService;
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
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    MyResponseRequest myResponseRequest = new MyResponseRequest(new Event());

    @RequestMapping(value = "/{eventId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getDepartment(@PathVariable("eventId") Long eventId,
                                                HttpServletRequest request){

        if (eventId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "event id must be not null");
        }
        Event event =  eventService.getById(eventId);

        if (event == null) {
            return myResponseRequest.notFoundRequest(request,eventId);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }


    @RequestMapping(value = "/{eventId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteDepartment(@PathVariable("eventId") Long eventId,
                                                   HttpServletRequest request){

        if (eventId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "event id must be not null");
        }
        Event event = eventService.getById(eventId);

        if (event == null) {
            return myResponseRequest.notFoundRequest(request,eventId);
        }
        eventService.delete(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{eventId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editDepartment(@PathVariable("eventId") Long eventId,
                                                 @RequestBody @Valid Event event,
                                                 HttpServletRequest request){

        if (eventId == null){
            return myResponseRequest.bedRequest(
                    request,
                    "event id must be not null");
        }
        Event event1 = eventService.getById(eventId);

        if (event1 == null) {
            return myResponseRequest.notFoundRequest(request,eventId);
        }
        event.setId(eventId);
        eventService.save(event);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllDepartment(HttpServletRequest request){
        List<Event> listEvent = eventService.getAll();
        if (listEvent.isEmpty()) {
            return myResponseRequest.notFoundRequest(request,null);
        }
        return new ResponseEntity<>(listEvent,HttpStatus.OK);
    }


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> saveDepartment(@RequestBody @Valid Event event,
                                                 UriComponentsBuilder builder,
                                                 HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (event == null){
            return myResponseRequest.bedRequest(
                    request,
                    "event id must be not null");
        }
        eventService.save(event);

        httpHeaders.setLocation(builder.path("/event/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(event,httpHeaders,HttpStatus.CREATED);
    }


}

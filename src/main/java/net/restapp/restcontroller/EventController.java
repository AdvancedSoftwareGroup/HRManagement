package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.dto.EventCreateDTO;
import net.restapp.dto.EventReadDTO;
import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.Event;
import net.restapp.servise.IService;
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
@RequestMapping("/event")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value="event", description="Operations pertaining to event")
public class EventController {

    @Autowired
    IService<Event> eventService;

    @Autowired
    DtoMapper mapper;

//----------------------get -------------------------

    @ApiOperation(value = "View event by id", response = EventReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view event"),
            @ApiResponse(code = 403, message = "Accessing retrieving the event you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "Wrong arguments")
    })
    @RequestMapping(value = "/{eventId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public EventReadDTO getDepartment(
            @ApiParam(value = "id of the Event", required = true) @PathVariable("eventId") Long eventId) {

        if (eventId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Event event = eventService.getById(eventId);

        if (event == null) {
            String msg = String.format("There is no event with id: %d", eventId);
            throw new EntityNotFoundException(msg);
        }
        return mapper.simpleFieldMap(event,EventReadDTO.class);
    }

//-------------------------delete --------------------------------
    @ApiOperation(value = "Delete event by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete event"),
            @ApiResponse(code = 403, message = "Accessing deleting the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The event you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{eventId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteDepartment(
            @ApiParam(value = "id of the Event", required = true) @PathVariable("eventId") Long eventId) {

        if (eventId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Event event = eventService.getById(eventId);

        if (event == null) {
            String msg = String.format("There is no departments with id: %d", eventId);
            throw new EntityNotFoundException(msg);
        }
        eventService.delete(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//----------------------------edit -----------------------------------
    @ApiOperation(value = "Update event by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update event"),
            @ApiResponse(code = 403, message = "Accessing updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The event you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{eventId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity editDepartment(@ApiParam(value = "id of the Event", required = true)  @PathVariable("eventId") Long eventId,
                                                 @ApiParam(value = "json body of the Event", required = true) @RequestBody @Valid EventCreateDTO dto) {

        if (eventId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Event event1 = eventService.getById(eventId);
        if (event1 == null) {
            String msg = String.format("There is no departments with id: %d", eventId);
            throw new EntityNotFoundException(msg);
        }
        if (dto == null) {
            throw new EntityNullException("event can't be null");
        }
        Event event = mapper.map(dto,Event.class);
        event.setId(eventId);
        eventService.save(event);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//-----------------------------add  ---------------------------
    @ApiOperation(value = "Save event to database", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to create event"),
            @ApiResponse(code = 403, message = "Accessing creating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The event you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity saveDepartment(
            @ApiParam(value = "json body of the Event", required = true) @RequestBody @Valid EventCreateDTO dto) {

        if (dto == null) {
            throw new EntityNullException("event can't be null");
        }
        Event event = mapper.map(dto,Event.class);
        eventService.save(event);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//-----------------------------getAll ---------------------------
    @ApiOperation(value = "Retrieve all events", response = EventReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Events successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to retrieve events"),
            @ApiResponse(code = 403, message = "Accessing retrieving events you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The is no events to retrieve")
    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<EventReadDTO> getAllDepartment() {
        List<Event> listEvent = eventService.getAll();
        if (listEvent.isEmpty()) {
            String msg = "There is no events ";
            throw new EntityNotFoundException(msg);
        }
        return  mapper.listSimpleFieldMap(listEvent,EventReadDTO.class);
    }


}

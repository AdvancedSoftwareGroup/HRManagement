package net.restapp.restcontroller;

import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.model.Position;
import net.restapp.servise.PositionService;
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
@RequestMapping("/position")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value="position", description="Operations pertaining to position in HRManagement")
public class PositionController {

    @Autowired
    PositionService positionService;

    @ApiOperation(value = "View position by id", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view event"),
            @ApiResponse(code = 403, message = "Accessing creating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getPosition(@ApiParam(value = "id of the Position", required = true) @PathVariable("positionId") Long positionId){

        if (positionId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Position position =  positionService.getById(positionId);

        if (position == null) {
            String msg = String.format("There is no position with id: %d", positionId);
            throw new EntityNotFoundException(msg);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }
    @ApiOperation(value = "Delete position by id", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete position"),
            @ApiResponse(code = 403, message = "Accessing deleting the position you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The position you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deletePosition(@ApiParam(value = "id of the Position", required = true) @PathVariable("positionId") Long positionId){

        if (positionId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Position position = positionService.getById(positionId);

        if (position == null) {
            String msg = String.format("There is no position with id: %d", positionId);
            throw new EntityNotFoundException(msg);
        }

        positionService.delete(positionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @ApiOperation(value = "Update position by id", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Position successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update position"),
            @ApiResponse(code = 403, message = "Accessing updating the position you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The position you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editPosition(@ApiParam(value = "id of the Position", required = true)  @PathVariable("positionId") Long positionId,
                                               @ApiParam(value = "json body of the Event", required = true) @RequestBody @Valid Position position){

        if (positionId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Position position1= positionService.getById(positionId);

        if (position1 == null) {
            String msg = String.format("There is no position with id: %d", positionId);
            throw new EntityNotFoundException(msg);
        }
        if (position == null) {
            throw new EntityNullException("position can't be null");
        }
        position.setId(positionId);
        positionService.save(position);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @ApiOperation(value = "Retrieve all positions", response = ArchiveSalary.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Positions successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to retrieve positions"),
            @ApiResponse(code = 403, message = "Accessing retrieving positions you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The is no positions to retrieve")
    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllPosition(){
        List<Position> positions = positionService.getAll();
        if (positions.isEmpty()) {
            String msg = "There is no position in database ";
            throw new EntityNotFoundException(msg);
        }
        return new ResponseEntity<>(positions,HttpStatus.OK);
    }

    @ApiOperation(value = "Save position to database", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Position successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to create position"),
            @ApiResponse(code = 403, message = "Accessing creating the position you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The position you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> savePosition(@ApiParam(value = "json body of the Event", required = true) @RequestBody @Valid Position position,
                                                 UriComponentsBuilder builder){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (position == null) {
            throw new EntityNullException("event can't be null");
        }
        positionService.save(position);

        httpHeaders.setLocation(builder.path("/position/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(position,httpHeaders,HttpStatus.CREATED);
    }





}

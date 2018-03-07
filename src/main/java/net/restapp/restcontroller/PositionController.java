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
public class PositionController {

    @Autowired
    PositionService positionService;


    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getPosition(@PathVariable("positionId") Long positionId){

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

    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deletePosition(@PathVariable("positionId") Long positionId){

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

    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editPosition(@PathVariable("positionId") Long positionId,
                                                 @RequestBody @Valid Position position){

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


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> savePosition(@RequestBody @Valid Position position,
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

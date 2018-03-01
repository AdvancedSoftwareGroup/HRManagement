package net.restapp.restcontroller;
import net.restapp.model.Position;
import net.restapp.servise.PositionService;
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
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService positionService;


    @RequestMapping(value = "/{positionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Position> getPosition (@PathVariable("positionId") Long positionId){
        if (positionId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Position position =  positionService.getById(positionId);

        if (position == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }
    @RequestMapping(value = "/{positionId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Position> deletePosition(@PathVariable("positionId") Long positionId){
        if (positionId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Position position = positionService.getById(positionId);
        if (position == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        positionService.delete(positionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Position>> getAllPosition(){
        List<Position> positions = positionService.getAll();
        if (positions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(positions,HttpStatus.OK);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Position> savePosition(@RequestBody @Valid Position position, UriComponentsBuilder builder){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (position == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        positionService.save(position);

        httpHeaders.setLocation(builder.path("/position/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(position,httpHeaders,HttpStatus.CREATED);
    }
}

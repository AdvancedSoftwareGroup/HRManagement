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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    MyResponseRequest myResponseRequest = new MyResponseRequest(new Position());

    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getPosition(@PathVariable("positionId") Long positionId,
                                                HttpServletRequest request){

        if (positionId == null){
            return myResponseRequest.bedRequest(request);
        }
        Position position =  positionService.getById(positionId);

        if (position == null) {
            return myResponseRequest.notFoundRequest(request,positionId);
        }
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deletePosition(@PathVariable("positionId") Long positionId,
                                                   HttpServletRequest request){

        if (positionId == null){
            return myResponseRequest.bedRequest(request);
        }
        Position position = positionService.getById(positionId);

        if (position == null) {
            return myResponseRequest.notFoundRequest(request,positionId);
        }
        positionService.delete(positionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> editPosition(@PathVariable("positionId") Long positionId,
                                                 @RequestBody @Valid Position position,
                                                 HttpServletRequest request){

        if (positionId == null){
            return myResponseRequest.bedRequest(request);
        }
        Position position1= positionService.getById(positionId);

        if (position1 == null) {
            return myResponseRequest.notFoundRequest(request,positionId);
        }
        position.setId(positionId);
        positionService.save(position);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAllPosition(HttpServletRequest request){
        List<Position> positions = positionService.getAll();
        if (positions.isEmpty()) {
            return myResponseRequest.notFoundRequest(request,null);
        }
        return new ResponseEntity<>(positions,HttpStatus.OK);
    }


    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> savePosition(@RequestBody @Valid Position position,
                                                 UriComponentsBuilder builder,
                                                 HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();

        if (position == null){
            return myResponseRequest.bedRequest(request);
        }
        positionService.save(position);

        httpHeaders.setLocation(builder.path("/position/getAll").buildAndExpand().toUri());
        return new ResponseEntity<>(position,httpHeaders,HttpStatus.CREATED);
    }





}

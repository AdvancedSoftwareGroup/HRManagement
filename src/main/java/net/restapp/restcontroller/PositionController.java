package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.dto.PositionCreateDTO;
import net.restapp.dto.PositionReadDTO;
import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.Position;
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
@RequestMapping("/position")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value="position", description="Operations pertaining to position")
public class PositionController {

    @Autowired
    IService<Position> positionService;

    @Autowired
    DtoMapper mapper;

//-------------------------------- get ----------------------------------------
    @ApiOperation(value = "View position by id", response = PositionReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view event"),
            @ApiResponse(code = 403, message = "Accessing creating the employee you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{positionId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PositionReadDTO getPosition(@ApiParam(value = "id of the Position", required = true) @PathVariable("positionId") Long positionId){

        if (positionId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Position position =  positionService.getById(positionId);

        if (position == null) {
            String msg = String.format("There is no position with id: %d", positionId);
            throw new EntityNotFoundException(msg);
        }
        return mapper.simpleFieldMap(position, PositionReadDTO.class);
    }


//--------------------------delete ---------------------------------------
    @ApiOperation(value = "Delete position by id", response = ResponseEntity.class)
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
    public ResponseEntity deletePosition(@ApiParam(value = "id of the Position", required = true) @PathVariable("positionId") Long positionId){

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

//-----------------------------------edit ----------------------------------------------
//
    @ApiOperation(value = "Update position by id", response = ResponseEntity.class)
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
    public ResponseEntity editPosition(@ApiParam(value = "id of the Position", required = true)  @PathVariable("positionId") Long positionId,
                                               @ApiParam(value = "json body of the Event", required = true) @RequestBody @Valid PositionCreateDTO dto){

        if (positionId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Position position1= positionService.getById(positionId);

        if (position1 == null) {
            String msg = String.format("There is no position with id: %d", positionId);
            throw new EntityNotFoundException(msg);
        }
        if (dto == null) {
            throw new EntityNullException("position can't be null");
        }
        Position position = mapper.map(dto, Position.class);
        position.setId(positionId);
        positionService.save(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//-----------------------------------getAll -------------------------------------------------------

    @ApiOperation(value = "Retrieve all positions", response = PositionReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Positions successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to retrieve positions"),
            @ApiResponse(code = 403, message = "Accessing retrieving positions you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The is no positions to retrieve")
    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PositionReadDTO> getAllPosition(){
        List<Position> positions = positionService.getAll();
        if (positions.isEmpty()) {
            String msg = "There is no position in database ";
            throw new EntityNotFoundException(msg);
        }
        return mapper.listSimpleFieldMap(positions,PositionReadDTO.class);
    }

//--------------------------------add --------------------------------------------
    @ApiOperation(value = "Save position to database", response = ResponseEntity.class)
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
    public ResponseEntity savePosition(
            @ApiParam(value = "json body of the Event", required = true) @RequestBody @Valid PositionCreateDTO dto){
        if (dto == null) {
            throw new EntityNullException("position can't be null");
        }
        Position position = mapper.map(dto, Position.class);
        positionService.save(position);

       return new ResponseEntity<>(HttpStatus.CREATED);
    }


}

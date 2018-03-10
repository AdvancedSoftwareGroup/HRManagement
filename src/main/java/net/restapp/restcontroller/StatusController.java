package net.restapp.restcontroller;

import io.swagger.annotations.*;
import net.restapp.dto.StatusCreateDTO;
import net.restapp.dto.StatusReadDTO;
import net.restapp.exception.EntityNullException;
import net.restapp.exception.PathVariableNullException;
import net.restapp.mapper.DtoMapper;
import net.restapp.model.ArchiveSalary;
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
import java.util.Map;

@RestController
@RequestMapping(value = "/status")
@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
@Api(value="status", description="Operations pertaining to status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    DtoMapper mapper;

//------------------------------get -----------------------------
    @ApiOperation(value = "View status by id", response = StatusReadDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Status successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view status"),
            @ApiResponse(code = 403, message = "Accessing retrieving the status you were trying to reach is forbidden"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StatusReadDTO getStatus(@ApiParam(value = "id of the Status", required = true) @PathVariable("statusId") Long statusId) {

        if (statusId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }

        Status status = statusService.getById(statusId);
        if (status == null) {
            String msg = String.format("There is no position with id: %d", statusId);
            throw new EntityNotFoundException(msg);
        }

        return mapper.simpleFieldMap(status,StatusReadDTO.class);
    }

//------------------------- Delete a Status ----------------------------------
    @ApiOperation(value = "Delete status by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status successfully deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to delete status"),
            @ApiResponse(code = 403, message = "Accessing deleting the status you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The status you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteStatus(@ApiParam(value = "id of the Status", required = true) @PathVariable("statusId") Long statusId) throws Exception {
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

//---------------------------------edit ----------------------------------------

    @ApiOperation(value = "Update status by id", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status successfully updated"),
            @ApiResponse(code = 401, message = "You are not authorized to update status"),
            @ApiResponse(code = 403, message = "Accessing updating the status you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The status you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{statusId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity editStatus(@ApiParam(value = "id of the Status", required = true) @PathVariable("statusId") Long statusId,
                                             @ApiParam(value = "json body of the status", required = true) @RequestBody @Valid StatusCreateDTO dto) throws Exception {

        if (statusId == null) {
            String msg = "PathVariable can't be null ";
            throw new PathVariableNullException(msg);
        }
        Status status1 = statusService.getById(statusId);

        if (status1 == null) {
            String msg = String.format("There is no position with id: %d", statusId);
            throw new EntityNotFoundException(msg);
        }
        if (dto == null) {
            throw new EntityNullException("status can't be null");
        }
        Status status = mapper.map(dto,Status.class);
        status.setId(statusId);
        statusService.save(status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//-----------------------------getAll -----------------------------------------
    @ApiOperation(value = "Retrieve list of status", response = StatusReadDTO.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of status successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to retrieve list of status"),
            @ApiResponse(code = 403, message = "Accessing retrieving list of status you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The is no status to retrieve")
    })
    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<StatusReadDTO> getAllStatus(){
        List<Status> statusList = statusService.getAll();
        if (statusList.isEmpty()) {
            String msg = "There is no status in database ";
            throw new EntityNotFoundException(msg);
        }
        return  mapper.listSimpleFieldMap(statusList,StatusReadDTO.class);

    }

//-----------------------------------add ---------------------------------------------
    @ApiOperation(value = "Save status to database", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Status successfully created"),
            @ApiResponse(code = 401, message = "You are not authorized to create status"),
            @ApiResponse(code = 403, message = "Accessing creating the status you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The status you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity saveStatus(
            @ApiParam(value = "json body of the status", required = true) @RequestBody @Valid StatusCreateDTO dto) throws Exception {

        if (dto == null) {
            throw new EntityNullException("status can't be null");
        }
        Status status = mapper.map(dto,Status.class);
        statusService.save(status);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}

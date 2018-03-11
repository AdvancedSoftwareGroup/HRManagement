package net.restapp.restcontroller;
import io.swagger.annotations.*;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.repository.RepoEmployees;
import net.restapp.servise.ArchiveSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salary")
@Api(value="archive", description="Operations pertaining to archive in HRManagement")
public class ArchiveSalaryController {

    @Autowired
    ArchiveSalaryService repoArchiveSalary;

    @Autowired
    RepoEmployees repoEmployees;
    @ApiOperation(value = "View a list of salary from archive by employee id and start/end date", response = ArchiveSalary.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view the salary for period"),
            @ApiResponse(code = 403, message = "Accessing the salary for period you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The salary for period you were trying to reach is not found")
    })
    @RequestMapping(value = "/{startDate}/{endDate}/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ArchiveSalary>> getSalaries(@ApiParam(value = "start Date of salary accrual date", required = true) @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                           @ApiParam(value = "end Date of salary accrual date", required = true) @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                           @ApiParam(value = "ID of employee", required = true) @PathVariable("employeeId") Long employeeId) {
        if (startDate == null || endDate == null || employeeId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employees employees = repoEmployees.findOne(employeeId);
        if (employees == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ArchiveSalary> archiveSalaries = repoArchiveSalary.findDateBetween(startDate, endDate, employees);

        if (archiveSalaries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(archiveSalaries, HttpStatus.OK);
    }
    @ApiOperation(value = "View salary from archive via Date", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view the salary via date"),
            @ApiResponse(code = 403, message = "Accessing the salary via Date you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The salary via Date you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{salaryViaDate}/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArchiveSalary> getSalary(@ApiParam(value = "Day of Salary", required = true) @PathVariable("salaryViaDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date salaryViaDate,
                                                   @ApiParam(value = "ID of employee", required = true) @PathVariable("employeeId") Long employeeId) {
        if (salaryViaDate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employees employee = repoEmployees.findOne(employeeId);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ArchiveSalary archiveSalary = repoArchiveSalary.findSalaryViaDate(salaryViaDate, employee);

        if (archiveSalary == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(archiveSalary, HttpStatus.OK);
    }
//    }
//    @ApiOperation(value = "add entry of salary to archive", response = ArchiveSalary.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successfully create entry of salary"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the salary for period"),
//            @ApiResponse(code = 403, message = "Accessing the salary for period you were trying to reach is forbidden"),
//            @ApiResponse(code = 400, message = "request is not correct")
//    })
//    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<ArchiveSalary> saveDepartment() {
////        if (archiveSalary == null) {
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
//
//        //Calendar cal = Calendar.getInstance();
//        for (int i = 0; i < 10; i++) {
//            //cal.add(Calendar.MONTH, -i);
//            ArchiveSalary archiveSalary1 = new ArchiveSalary();
//            Date date = new Date(2017, 3+i, 1);
//            Employees employees = repoEmployees.findOne(3l);
//            archiveSalary1.setDate(date);
//            archiveSalary1.setMonthSalary(new BigDecimal(700+i*10));
//            archiveSalary1.setEmployee(employees);
//            repoArchiveSalary.save(archiveSalary1);
//        }
//
//
//
////        Date today = cal.getTime();
////
////
////        cal.add(Calendar.MONTH, -countMonth);
////        Date before = cal.getTime();
////
////
////
////
////                ArchiveSalary archiveSalary1 = new ArchiveSalary();
////
////                Date date = new Date(1900, 3, 1);
////
////                Employees employees = repoEmployees.findOne(2l);
////                archiveSalary1.setDate(date);
////                archiveSalary1.setMonthSalary(new BigDecimal(70000));
////                archiveSalary1.setEmployee(employees);
////                repoArchiveSalary.save(archiveSalary1);
////
////
//
//
//        //repoArchiveSalary.save(archiveSalary1);
//        return new ResponseEntity<>( HttpStatus.CREATED);
//    }

    @ApiOperation(value = "View list of all salary entries from archive", response = ArchiveSalary.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of salary entries"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of salary entries"),
            @ApiResponse(code = 403, message = "Accessing the list of salary entries you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The salary entries not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ArchiveSalary>> getAllSalaries(){
        List<ArchiveSalary> archiveSalaries = repoArchiveSalary.getAll();
        if (archiveSalaries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(archiveSalaries,HttpStatus.OK);
    }
    @ApiOperation(value = "View entry of salary from archive by id", response = ArchiveSalary.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved entry of salary"),
            @ApiResponse(code = 401, message = "You are not authorized to view entry of salary"),
            @ApiResponse(code = 403, message = "Accessing the entry of salary you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The entry of salary you were trying to reach is not found"),
            @ApiResponse(code = 400, message = "request is not correct")
    })
    @RequestMapping(value = "/{archiveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArchiveSalary> getDepartment(@ApiParam(value = "ID of salary entry in archive", required = true) @PathVariable("archiveId") Long archiveId){
        if (archiveId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ArchiveSalary archiveSalary =  repoArchiveSalary.getById(archiveId);

        if (archiveSalary == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(archiveSalary, HttpStatus.OK);
    }
}
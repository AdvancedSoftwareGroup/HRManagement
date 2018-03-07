package net.restapp.restcontroller;
import net.restapp.model.ArchiveSalary;
import net.restapp.model.Employees;
import net.restapp.model.Position;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salary")
public class ArchiveSalaryController {

    @Autowired
    ArchiveSalaryService repoArchiveSalary;

    @Autowired
    RepoEmployees repoEmployees;

    @RequestMapping(value = "/{startDate}/{endDate}/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ArchiveSalary>> getSalaries(@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                           @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                           @PathVariable("employeeId") Long employeeId) {
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

    @RequestMapping(value = "/{salaryViaDate}/{employeeId}")
    public ResponseEntity<ArchiveSalary> getSalary(@PathVariable("salaryViaDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date salaryViaDate,
                                                   @PathVariable("employeeId") Long employeeId) {
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

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArchiveSalary> saveDepartment(@RequestBody @Valid ArchiveSalary archiveSalary) {
        if (archiveSalary == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repoArchiveSalary.save(archiveSalary);
        return new ResponseEntity<>(archiveSalary, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addadd", method = RequestMethod.POST)
    public void saveDepartment(){
        ArchiveSalary archiveSalary = new ArchiveSalary();

        Date date = new Date(1900, 3, 1);
        Employees employees = new Employees();
        employees.setFirstName("Ivan");
        employees.setLastName("Ivanov");
        Position position = new Position();
        position.setName("Java");
        employees.setPosition(position);
        repoEmployees.save(employees);
        Employees employees2 = repoEmployees.findOne(1l);
        archiveSalary.setDate(date);
        archiveSalary.setMonthSalary(new BigDecimal(70000));
        archiveSalary.setEmployees(employees2);

        repoArchiveSalary.save(archiveSalary);

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ArchiveSalary>> getAllSalaries(){
        List<ArchiveSalary> archiveSalaries = repoArchiveSalary.getAll();
        if (archiveSalaries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(archiveSalaries,HttpStatus.OK);
    }

    @RequestMapping(value = "/{archiveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArchiveSalary> getDepartment(@PathVariable("archiveId") Long archiveId){
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
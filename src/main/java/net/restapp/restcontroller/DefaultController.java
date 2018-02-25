package net.restapp.restcontroller;

import net.restapp.servise.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    private void startRest(){

    }
}

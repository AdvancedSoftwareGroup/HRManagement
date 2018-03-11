package net.restapp.servise.impl;

import net.restapp.model.WorkingHours;
import net.restapp.servise.CountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CountServiceImp implements CountService {

    public BigDecimal calculateSalaryOfEvent(WorkingHours workingHours){

        //todo: if we send employee to vocation(avaliable vacation days, vacation salary)
        //todo: sick

        BigDecimal salaryPerHour = workingHours.getEmployees().getPosition().getSalary();
        BigDecimal statusCoef = workingHours.getStatus().getSalary_coef();
        BigDecimal hours = workingHours.getHours();
//todo: thinking about add a working hour (dint work when statusid =2 )
        return hours.multiply(salaryPerHour.multiply(statusCoef));
    }
}

package net.restapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HRManagementApp {

    public static void main(String[] args) {

        SpringApplication.run(HRManagementApp.class, args);
    }
}

package net.restapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.restapp.model.*;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;


@JsonComponent
public class EmployeesJsonSerializer extends JsonSerializer<Employees> {

    @Override
    public void serialize(Employees employees, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", employees.getId());
        jsonGenerator.writeStringField("firstName", employees.getFirstName());
        jsonGenerator.writeStringField("lastName", employees.getLastName());
        jsonGenerator.writeNumberField("availableVacationDay", employees.getAvailableVacationDay());
        jsonGenerator.writeNumberField("experience", employees.getExperience());
        jsonGenerator.writeStringField("startWorkingDate", employees.getStartWorkingDate().toString());

        User user = employees.getUser();
        if (user != null) {
            jsonGenerator.writeObjectFieldStart("user");
            jsonGenerator.writeNumberField("id", user.getId());
            jsonGenerator.writeStringField("email", user.getEmail());
            Role role = user.getRole();
            if (role != null) {
                jsonGenerator.writeObjectFieldStart("role");
                jsonGenerator.writeNumberField("id", role.getId());
                jsonGenerator.writeStringField("name", role.getName());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndObject();
        } else {
            jsonGenerator.writeNullField("user");
        }

        Position position = employees.getPosition();
        if (position != null) {
            jsonGenerator.writeObjectFieldStart("position");
            jsonGenerator.writeNumberField("id", position.getId());
            jsonGenerator.writeStringField("name", position.getName());
            jsonGenerator.writeNumberField("dayForVacation", position.getDayForVacation());
            jsonGenerator.writeNumberField("salary", position.getSalary());
            Department department = position.getDepartment();
            if (department != null) {
                jsonGenerator.writeObjectFieldStart("department");
                jsonGenerator.writeNumberField("id", department.getId());
                jsonGenerator.writeStringField("name", department.getName());
                jsonGenerator.writeEndObject();
            } else {
                jsonGenerator.writeNullField("department");
            }
            jsonGenerator.writeEndObject();
        } else {
            jsonGenerator.writeNullField("position");
        }
//        if (employees.getArchiveSalary() != null) {
//            jsonGenerator.writeArrayFieldStart("archiveSalary");
//            jsonGenerator.writeStartArray();
//            for(ArchiveSalary archiveSalary: employees.getArchiveSalary()) {
//                jsonGenerator.writeStartObject();
//                jsonGenerator.writeNumberField("id", archiveSalary.getId());
//                jsonGenerator.writeNumberField("monthSalary", archiveSalary.getMonthSalary());
//                jsonGenerator.writeStringField("date", archiveSalary.getDate().toString());
//                jsonGenerator.writeEndObject();
//            }
//            jsonGenerator.writeEndArray();
//            jsonGenerator.writeEndObject();
//        }
        jsonGenerator.writeEndObject();
    }
}



package net.restapp.integration;
import net.restapp.model.Department;
import net.restapp.model.Position;
import net.restapp.repository.RepoDepartment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DepartmentIntegrTest {
    @Autowired
    RepoDepartment repoDepartment;
    @LocalServerPort
    private int port;
    /**
     * The test-method that create Department
     */
    @Test
    public void createDepTest() {
        given().
                auth().basic("svitlana.anulich@gmail.com", "22222222").
                contentType("application/json").
                body(createDepartment()).
                when().
                post("http://localhost:" + port + "/api/department/add").
                then().
                assertThat().statusCode(201);
    }
    /**
     * The test-method that get one Department by ID.
     */
    @Test
    public void readDepTest() {
        given().
                auth().basic("svitlana.anulich@gmail.com", "22222222").
                when().
                get("http://localhost:" + port + "/api/department/{id}", 1007).
                then().
                assertThat().statusCode(200).
                assertThat().body("id", equalTo(1007)).
                assertThat().body("name", equalTo("DepName007"));
    }
    /**
     * The test-method for update Department by ID.
     */
    @Test
    public void putNewNameValueToDepTest() {
        Department department = createDepartment();
        department.setName("DepNameS");
        given().
                auth().basic("svitlana.anulich@gmail.com", "22222222").
                contentType("application/json").
                body(department).
                when().
                put("http://localhost:" + port + "/api/department/{id}", 1007).
                then().
                assertThat().statusCode(204);
    }
    /**
     * The test-method for read all Departments
     */
    @Test
    public void readAllDepTest(){
        given().
                auth().basic("svitlana.anulich@gmail.com", "22222222").
                when().
                get("http://localhost:" + port + "/api/department/getAll").
                then().
                assertThat().statusCode(200).
                assertThat().body("[0].id", equalTo(1007));
    }
    /**
     * The test-method for delete Department by ID
     */
    @Test
    public void deleteDepTest(){
        given().
                auth().basic("svitlana.anulich@gmail.com", "22222222").
                when().
                delete("http://localhost:" + port + "/api/department/{id}", 1007).
                then().
                assertThat().statusCode(204);
    }
    /**
     * Test entity which is used in test - methods of this class
     */
    public static Department createDepartment(){
        Department department1 = new Department();
        department1.setId(1007l);
        department1.setName("DepName007");
        Position position1 = new Position();
        position1.setSalary(new BigDecimal(5000));
        position1.setDayForVacation(40);
        position1.setName("Junior+");
        position1.setDepartment(department1);
        Position position2 = new Position();
        position2.setSalary(new BigDecimal(6000));
        position2.setDayForVacation(20);
        position2.setName("Junior-");
        position2.setDepartment(department1);
        List<Position> positions = new ArrayList<>();
        positions.add(position1);
        positions.add(position2);
        department1.setPositions(positions);
        return department1;
    }
}


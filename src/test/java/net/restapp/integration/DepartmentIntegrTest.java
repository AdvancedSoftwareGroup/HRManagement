package net.restapp.integration;
import net.restapp.model.Department;
import net.restapp.repository.RepoDepartment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
    public void create_dep_Test() {
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
    public void read_dep_Test() {
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
    public void put_new_name_value_to_dep_Test() {
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
    public void read_all_dep_Test(){
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
    public void delete_dep_Test(){
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
        return department1;
    }
}


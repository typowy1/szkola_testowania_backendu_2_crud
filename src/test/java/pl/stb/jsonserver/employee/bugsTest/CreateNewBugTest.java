package pl.stb.jsonserver.employee.bugsTest;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class CreateNewBugTest {

    private static Faker faker;

    private static final String BASE_URL = "http://localhost:3000/bugs";
    private String title;
    private String randomDescription;
    private int randomEmployeeId;
    private String randomStatus;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach() {
        title = faker.book().title();
        randomDescription = faker.lorem().characters(3, 10);
        randomEmployeeId = faker.number().numberBetween(1, 5);
        randomStatus = "open";
    }


    @Test
    void createNewBug() {
        JSONObject bug = new JSONObject();
        bug.put("title", title);
        bug.put("description", randomDescription);
        bug.put("employeeId", randomEmployeeId);
        bug.put("status", randomStatus);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .post(BASE_URL)
                .prettyPeek()
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(title, jsonPath.getString("title"));
        Assertions.assertEquals(randomDescription, jsonPath.getString("description"));
        Assertions.assertEquals(randomEmployeeId, jsonPath.getInt("employeeId"));
        Assertions.assertEquals(randomStatus, jsonPath.getString("status"));
    }
}

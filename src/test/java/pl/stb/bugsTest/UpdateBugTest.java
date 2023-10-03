package pl.stb.bugsTest;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateBugTest {
    private static Faker faker;

    private static final String BASE_URL = "http://localhost:3000/bugs";
    private String title;
    private String randomDescription;

    @BeforeAll
    static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach() {
        title = faker.book().title();
        randomDescription = faker.lorem().characters(3, 10);
    }

    @Test
    void updateBugTest() {
        JSONObject bug = new JSONObject();
        bug.put("title", title);
        bug.put("description", randomDescription);
        bug.put("employeeId", 1);
        bug.put("status", "open");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .pathParam("id", 1)
                .when()
                .put(BASE_URL + "/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(title, jsonPath.getString("title"));
        Assertions.assertEquals(randomDescription, jsonPath.getString("description"));
        Assertions.assertEquals(1, jsonPath.getInt("employeeId"));
        Assertions.assertEquals("open", jsonPath.getString("status"));
    }

    @Test
    void partialUpdateBugTest() {
        JSONObject bug = new JSONObject();
        bug.put("title", title);
        bug.put("description", randomDescription);


        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .pathParam("id", 1)
                .when()
                .patch(BASE_URL + "/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(title, jsonPath.getString("title"));
        Assertions.assertEquals(randomDescription, jsonPath.getString("description"));
        Assertions.assertEquals(1, jsonPath.getInt("employeeId"));
        Assertions.assertEquals("open", jsonPath.getString("status"));
    }
}

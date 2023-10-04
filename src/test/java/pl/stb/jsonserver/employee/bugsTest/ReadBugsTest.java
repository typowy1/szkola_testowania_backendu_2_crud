package pl.stb.jsonserver.employee.bugsTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ReadBugsTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void readAllBugsTest() {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        List<Integer> ids = jsonPath.getList("id");
        Assertions.assertTrue(ids.size() > 0);
    }

    @Test
    void readOneBugTest() {
        Response response = given()
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("Incorrect response code after PATH to /bugs", jsonPath.getString("title"));
        Assertions.assertEquals("When I send a PATH request to /bugs, instead of status code 200, I'm getting 404", jsonPath.getString("description"));
        Assertions.assertEquals(1, jsonPath.getInt("employeeId"));
        Assertions.assertEquals("open", jsonPath.getString("status"));
    }

    @Test
    void readOneBugWithPathVariableTest() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get(BASE_URL + "/{id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("Incorrect response code after PATH to /bugs", jsonPath.getString("title"));
        Assertions.assertEquals("When I send a PATH request to /bugs, instead of status code 200, I'm getting 404", jsonPath.getString("description"));
        Assertions.assertEquals(1, jsonPath.getInt("employeeId"));
        Assertions.assertEquals("open", jsonPath.getString("status"));
    }

    @Test
    void readOneBugWithQueryParamsTest() {
        Response response = given()
                .queryParams("title", "Incorrect response code after PATH to /bugs")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("Incorrect response code after PATH to /bugs", jsonPath.getList("title").get(0));
        Assertions.assertEquals("When I send a PATH request to /bugs, instead of status code 200, I'm getting 404",
                jsonPath.getList("description").get(0));
        Assertions.assertEquals(1, jsonPath.getList("employeeId").get(0));
        Assertions.assertEquals("open", jsonPath.getList("status").get(0));
    }

}

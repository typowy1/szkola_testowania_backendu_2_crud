package pl.stb.jsonserver.employee.bugsTest;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class DeleteBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void deleteEmployeeTest() {
        Response response = given()
                .when()
                .pathParam("id", 9)
                .delete(BASE_URL + "/{id}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }
}

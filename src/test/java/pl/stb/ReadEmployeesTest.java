package pl.stb;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ReadEmployeesTest {

    public static final String BASE_URL = "http://localhost:3000/employees";

    @Test
    void readAllEmployeesTest() {

        Response response = given()
                .when()
                .get(BASE_URL);

        Assertions.assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPeek()); // wyświetlenie całego response body z hederami itd
        System.out.println(response.prettyPrint()); // wyświetlenie response body

        JsonPath jsonPath = response.jsonPath();
        List<String> firstNames = jsonPath.getList("firstName");

        System.out.println(firstNames);
        Assertions.assertTrue(firstNames.size() > 0);
    }

    @Test
    void readOneEmployeeTest() {
// tak powinno się pisać
        Response response = given()
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .extract().response();


//        Assertions.assertEquals(200, response.getStatusCode()); // to już nie potrzebne

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));

//        firstName	"Bartek"
//        lastName	"Czarny"
//        username	"bczarny"
//        email	"bczarny@testerprogramuje.pl"

    }

    @Test
    void readOneUserWithPathVariableTest() {
        Response response = given()
                .pathParam("id", 1)
                .when()
                .get(BASE_URL + "/{id}");

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void readOneUserWithQueryParamsTest() {
        Response response = given()
                .queryParams("firstName", "Bartek")
                .when()
                .get(BASE_URL);

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getList("firstName").get(0));
        Assertions.assertEquals("Czarny", json.getList("lastName").get(0));
        Assertions.assertEquals("bczarny", json.getList("username").get(0));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getList("email").get(0));
    }

//    @Test
//    void readOneEmployeeV2Test() {
//
//    można też tak ale to słabo wygląda//        given()
//                .when()
//                .get("http://localhost:3000/employees/1")
//                .then()
//                .statusCode(200)
//                        .body("firstName", Matchers.equalTo("Bartek"))
//                        .body("lastName", Matchers.equalTo("Czarny"))
//                        .body("username", Matchers.equalTo("bczarny"))
//                        .body("email", Matchers.equalTo("bczarny@testerprogramuje.pl"));
////
//    }
}

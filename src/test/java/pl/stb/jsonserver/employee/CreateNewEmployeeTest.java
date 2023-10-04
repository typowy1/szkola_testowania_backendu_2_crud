package pl.stb.jsonserver.employee;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateNewEmployeeTest {

    @Test
    void createNewEmployeeTest() {

        JSONObject company = new JSONObject();
        company.put("companyName", "Akademia QA");
        company.put("taxNumber", "531-1593-430");
        company.put("companyPhone", "731-111-111");

        JSONObject address = new JSONObject();
        address.put("street", "Ul. Sezamkowa");
        address.put("suite", "8");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("firstName", "Bartek");
        employee.put("lastName", "Czarny");
        employee.put("username", "bczarny");
        employee.put("email", "12-bczarny@testerprogramuje.pl");
        employee.put("phone", "731-111-111");
        employee.put("website", "testerprogramuje.pl");
        employee.put("role", "qa");
        employee.put("type", "b2b");
        employee.put("address", address);
        employee.put("company", company);

        System.out.println(employee);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())// jak mamy objekt to trzeba zamienić na stringa
                .when()
                .post("http://localhost:3000/employees")
                .prettyPeek()
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("12-bczarny@testerprogramuje.pl", json.getString("email"));
    }
}

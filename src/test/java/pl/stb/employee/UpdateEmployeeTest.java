package pl.stb.employee;

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

class UpdateEmployeeTest {

    private static Faker faker;
    private String randomEmail;
    private String randomFirstName;
    private String randomLastName;

    @BeforeAll// tu zawsze metody statyczne, uruchomi sie przed wszystkimi testami
    static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach // uruchomi sie przed każdym testem, nie musi być statyczne
    void beforeEach(){
        randomEmail = faker.internet().emailAddress();
        randomFirstName = faker.name().firstName();
        randomLastName = faker.name().lastName();
    }

    @Test
    void updateEmployeeTest() {
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
        employee.put("firstName", randomFirstName);
        employee.put("lastName", randomLastName);
        employee.put("username", "bczarny");
        employee.put("email", randomEmail);
        employee.put("phone", "731-111-111");
        employee.put("website", "testerprogramuje.pl");
        employee.put("role", "qa");
        employee.put("type", "b2b");
        employee.put("address", address);
        employee.put("company", company);

        System.out.println(employee);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .put("http://localhost:3000/employees/1")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomFirstName, json.getString("firstName"));
        Assertions.assertEquals(randomLastName, json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals(randomEmail, json.getString("email"));
    }

    @Test
    void partialUpdateEmployeeTest() {
        JSONObject employee = new JSONObject();
        employee.put("email", randomEmail);
        System.out.println(employee);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .patch("http://localhost:3000/employees/1")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomEmail, json.getString("email"));
    }
}

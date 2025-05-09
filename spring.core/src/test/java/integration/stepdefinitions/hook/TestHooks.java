package integration.stepdefinitions.hook;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestHooks {

    public static Response response;
    public static String token;

    @Before
    public void setupTestData()  {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

}

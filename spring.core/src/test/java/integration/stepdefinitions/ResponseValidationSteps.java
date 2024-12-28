package integration.stepdefinitions;

import integration.stepdefinitions.hook.TestHooks;
import io.cucumber.java.en.Then;

import static org.hamcrest.Matchers.equalTo;


public class ResponseValidationSteps {

    @Then("the response status should be {int}")
    public void verifyResponseStatus(int statusCode) {
        TestHooks.response.then().statusCode(statusCode);
    }

    @Then("the response should contain message {string}")
    public void verifyResponseMessage(String message) {
        TestHooks.response.then().body("message", equalTo(message));
    }
}

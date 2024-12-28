package integration.stepdefinitions;

import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import integration.stepdefinitions.hook.TestHooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static org.hamcrest.core.IsNull.notNullValue;

public class AuthStep {

    private final JwtRequest jwtRequest;
    private final TraineeDTO traineeDTO;
    private final TrainerDTO trainerDTO;
    private final UserDTO userDTO;
    private final UserDTO userDTO1;


    public  AuthStep(){
        jwtRequest = new JwtRequest("testUsername", "testPass");

        String uniqueSuffix = String.valueOf(System.currentTimeMillis());

        userDTO = new UserDTO();
        userDTO.setLastName("testLastName" + uniqueSuffix);
        userDTO.setFirstName("testFirstName" + uniqueSuffix);

        userDTO1 = new UserDTO();
        userDTO1.setFirstName("testFirstName1" + (uniqueSuffix + "2"));
        userDTO1.setLastName("testLastName1" + (uniqueSuffix + "2"));

        traineeDTO = new TraineeDTO(userDTO, LocalDate.now(), new ArrayList<>(), new ArrayList<>(),
                "testAddress");
        trainerDTO = new TrainerDTO(userDTO1, new TrainingTypeDTO("TestName"), new ArrayList<>(), new HashSet<>());
    }


    @When("a user registers as a trainee with first name {string} and last name {string}")
    public void registrationUserTrainee (String firstName, String lastName ){
        userDTO.toBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        traineeDTO.setUser(userDTO);
        TestHooks.response = RestAssured.given()
                .contentType("application/json")
                .body(traineeDTO)
                .post("/api/trainees/register");
    }

    @When("a user registers as a trainer with first name {string} and last name {string}")
    public void registrationUserTrainer(String firstName, String lastName){
        userDTO1.toBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        trainerDTO.setUser(userDTO1);
        TestHooks.response = RestAssured.given()
                .contentType("application/json")
                .body(trainerDTO)
                .post("/api/trainers/register");
    }

    @When("a user login with registered credentials")
    public void loginUser() {
        jwtRequest.setUsername("testUsername");
        jwtRequest.setPassword("testPass");

        TestHooks.response = RestAssured.given()
                .contentType("application/json")
                .body(jwtRequest)
                .post("/api/auth");
    }

    @Then("the response should contain a valid token")
    public void verifyResponseToken() {
        TestHooks.response.then().body("token", notNullValue());
    }

    @When("a user login with username {string} and password {string}")
    public void loginUser(String username, String password) {
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);

        TestHooks.response = RestAssured.given()
                .contentType("application/json")
                .body(jwtRequest)
                .post("/api/auth");
    }

}

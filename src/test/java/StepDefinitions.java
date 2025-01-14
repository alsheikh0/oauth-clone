import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.example.UserRegistration;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions{
    private String email;
    private String password;
    private String username;
    private boolean isRegistered;
    //private UserRegistration userRegistration;

    @Given("the user provides a valid email {String}")
    public void givenValidEmail(String email){
        this.email = email;
    }
    @Given("the user provides a valid username {String}")
    public void givenValidUsername(String username){
        this.username = username;
    }
    @Given("the user provides a valid password{String}")
    public void givenValidPassword(String password){
        this.password = password;
    }

    @Then("the user should be successfully registered")
    @DisplayName("Test Passed. C.")
    public void thenUserShouldBeRegistered() {
        assertTrue(UserRegistration.registerUser(email, username, password));
    }

}
package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utilities.ConfigReader;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        context.loginPage().open();
        Assert.assertTrue(context.loginPage().isDisplayed(), "Login page was not displayed");
    }

    @Given("the user is logged in with valid credentials")
    public void theUserIsLoggedInWithValidCredentials() {
        theUserIsOnTheLoginPage();
        theUserLogsInWithValidCredentials();
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        context.loginPage().login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsInWithUsernameAndPassword(String username, String password) {
        context.loginPage().login(username, password);
    }

    @Then("the login error message should be {string}")
    public void theLoginErrorMessageShouldBe(String expected) {
        Assert.assertEquals(context.loginPage().getErrorMessage(), expected, "Unexpected login error message");
    }
}

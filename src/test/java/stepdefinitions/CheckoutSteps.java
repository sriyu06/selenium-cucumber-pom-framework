package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class CheckoutSteps {

    private final TestContext context;

    public CheckoutSteps(TestContext context) {
        this.context = context;
    }

    @When("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {
        context.cartPage().proceedToCheckout();
        Assert.assertTrue(context.checkoutPage().isInformationStepDisplayed(),
                "Checkout information page was not displayed");
    }

    @When("the user enters first name {string}, last name {string} and postal code {string}")
    public void theUserEntersCheckoutInformation(String first, String last, String postalCode) {
        context.checkoutPage().enterInformation(first, last, postalCode);
    }

    @When("the user continues to the order overview")
    public void theUserContinuesToTheOrderOverview() {
        context.checkoutPage().clickContinue();
    }

    @When("the user finishes the order")
    public void theUserFinishesTheOrder() {
        context.checkoutPage().finish();
    }

    @Then("the checkout error message should be {string}")
    public void theCheckoutErrorMessageShouldBe(String expected) {
        Assert.assertEquals(context.checkoutPage().getErrorMessage(), expected, "Unexpected checkout error");
    }

    @Then("the order overview should be displayed")
    public void theOrderOverviewShouldBeDisplayed() {
        Assert.assertTrue(context.checkoutPage().isOverviewDisplayed(), "Order overview was not displayed");
    }

    @Then("the order overview should list {string}")
    public void theOrderOverviewShouldList(String product) {
        List<String> names = context.checkoutPage().getOverviewItemNames();
        Assert.assertTrue(names.contains(product), "Expected '" + product + "' in overview but found " + names);
    }

    @Then("the order totals should be calculated correctly")
    public void theOrderTotalsShouldBeCalculatedCorrectly() {
        double sumOfItems = context.checkoutPage().getSumOfItemPrices();
        double itemTotal = context.checkoutPage().getItemTotal();
        double tax = context.checkoutPage().getTax();
        double total = context.checkoutPage().getTotal();

        Assert.assertEquals(itemTotal, sumOfItems, 0.001, "Item total does not equal the sum of item prices");
        Assert.assertEquals(total, itemTotal + tax, 0.001, "Total does not equal item total + tax");
    }

    @Then("the order confirmation {string} should be displayed")
    public void theOrderConfirmationShouldBeDisplayed(String expectedHeader) {
        Assert.assertTrue(context.checkoutPage().isOrderComplete(), "Order complete page was not displayed");
        Assert.assertEquals(context.checkoutPage().getConfirmationHeader(), expectedHeader);
    }
}

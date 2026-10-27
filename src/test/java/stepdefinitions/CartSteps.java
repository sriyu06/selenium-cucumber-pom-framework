package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class CartSteps {

    private final TestContext context;

    public CartSteps(TestContext context) {
        this.context = context;
    }

    @When("the user adds {string} to the cart")
    public void theUserAddsToTheCart(String product) {
        context.inventoryPage().addToCart(product);
    }

    @When("the user removes {string} from the inventory page")
    public void theUserRemovesFromTheInventoryPage(String product) {
        context.inventoryPage().removeFromCart(product);
    }

    @When("the user removes {string} from the cart page")
    public void theUserRemovesFromTheCartPage(String product) {
        context.cartPage().removeItem(product);
    }

    @When("the user opens the cart")
    public void theUserOpensTheCart() {
        context.inventoryPage().openCart();
        Assert.assertTrue(context.cartPage().isLoaded(), "Cart page was not displayed");
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
        Assert.assertTrue(context.inventoryPage().cartBadgeShowsCount(expected),
                "Expected cart badge " + expected + " but found " + context.inventoryPage().getCartBadgeCount());
    }

    @Then("the cart badge should not be displayed")
    public void theCartBadgeShouldNotBeDisplayed() {
        Assert.assertTrue(context.inventoryPage().cartBadgeShowsCount(0),
                "Cart badge is still displayed with " + context.inventoryPage().getCartBadgeCount());
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String product) {
        List<String> names = context.cartPage().getItemNames();
        Assert.assertTrue(names.contains(product), "Expected '" + product + "' in cart but found " + names);
    }

    @Then("the cart item {string} should have price {string} and quantity {int}")
    public void theCartItemShouldHavePriceAndQuantity(String product, String price, int quantity) {
        Assert.assertEquals(context.cartPage().getItemPrice(product), price, "Wrong price for " + product);
        Assert.assertEquals(context.cartPage().getItemQuantity(product), quantity, "Wrong quantity for " + product);
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assert.assertEquals(context.cartPage().getItemCount(), 0, "Cart is not empty");
    }
}

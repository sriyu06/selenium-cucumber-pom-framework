package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventorySteps {

    private final TestContext context;

    public InventorySteps(TestContext context) {
        this.context = context;
    }

    @Then("the inventory page should be displayed")
    public void theInventoryPageShouldBeDisplayed() {
        Assert.assertTrue(context.inventoryPage().isLoaded(), "Inventory page was not displayed after login");
        Assert.assertEquals(context.inventoryPage().getTitle(), "Products");
    }

    @Then("the inventory should list {int} products")
    public void theInventoryShouldListProducts(int expectedCount) {
        Assert.assertEquals(context.inventoryPage().getProductCount(), expectedCount, "Unexpected product count");
    }

    @Then("every product should show a name, description, price and image")
    public void everyProductShouldShowCompleteDetails() {
        Assert.assertTrue(context.inventoryPage().allProductsShowCompleteDetails(),
                "At least one product is missing its name, description, price or image");
    }

    @When("the user sorts the products by {string}")
    public void theUserSortsTheProductsBy(String option) {
        context.inventoryPage().sortBy(option);
    }

    @Then("the products should be ordered by {string}")
    public void theProductsShouldBeOrderedBy(String option) {
        switch (option) {
            case "Name (A to Z)":
                assertNamesSorted(false);
                break;
            case "Name (Z to A)":
                assertNamesSorted(true);
                break;
            case "Price (low to high)":
                assertPricesSorted(false);
                break;
            case "Price (high to low)":
                assertPricesSorted(true);
                break;
            default:
                throw new IllegalArgumentException("Unknown sort option: " + option);
        }
    }

    private void assertNamesSorted(boolean descending) {
        List<String> actual = context.inventoryPage().getProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        if (descending) {
            Collections.reverse(expected);
        }
        Assert.assertEquals(actual, expected, "Product names are not sorted as expected");
    }

    private void assertPricesSorted(boolean descending) {
        List<Double> actual = context.inventoryPage().getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        if (descending) {
            Collections.reverse(expected);
        }
        Assert.assertEquals(actual, expected, "Product prices are not sorted as expected");
    }
}

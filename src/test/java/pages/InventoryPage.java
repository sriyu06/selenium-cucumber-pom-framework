package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By inventoryItems = By.className("inventory_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return waitForUrlContains("inventory.html") && isDisplayed(pageTitle);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public int getProductCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItems)).size();
    }

    public List<String> getProductNames() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemNames))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemPrices))
                .stream().map(e -> parsePrice(e.getText())).collect(Collectors.toList());
    }

    /** True when every product card has a name, description, "$" price and an image source. */
    public boolean allProductsShowCompleteDetails() {
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItems));
        return items.stream().allMatch(item ->
                !item.findElement(By.className("inventory_item_name")).getText().isBlank()
                        && !item.findElement(By.className("inventory_item_desc")).getText().isBlank()
                        && item.findElement(By.className("inventory_item_price")).getText().startsWith("$")
                        && !item.findElement(By.tagName("img")).getAttribute("src").isBlank());
    }

    public void sortBy(String visibleText) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown)))
                .selectByVisibleText(visibleText);
    }

    public void addToCart(String productName) {
        click(By.id("add-to-cart-" + slug(productName)));
    }

    public void removeFromCart(String productName) {
        click(By.id("remove-" + slug(productName)));
    }
}

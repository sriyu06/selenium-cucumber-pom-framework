package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return waitForUrlContains("cart.html");
    }

    /** Locates the cart row for one product by its name. */
    private By rowFor(String productName) {
        return By.xpath("//div[@class='cart_item'][.//div[@class='inventory_item_name' and normalize-space()='"
                + productName + "']]");
    }

    /** Names of all products in the cart (empty list when the cart is empty - no waiting for absent rows). */
    public List<String> getItemNames() {
        return driver.findElements(itemNames).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getItemPrice(String productName) {
        return driver.findElement(rowFor(productName)).findElement(By.className("inventory_item_price")).getText();
    }

    public int getItemQuantity(String productName) {
        return Integer.parseInt(driver.findElement(rowFor(productName))
                .findElement(By.className("cart_quantity")).getText().trim());
    }

    public void removeItem(String productName) {
        click(By.id("remove-" + slug(productName)));
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public void proceedToCheckout() {
        click(checkoutButton);
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.List;

/**
 * Common behaviour shared by every page: explicit-wait helpers and the
 * shopping-cart header (badge + cart link) that appears on all logged-in pages.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds")));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean waitForUrlContains(String fragment) {
        try {
            return wait.until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected static double parsePrice(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    /** Slug used by SauceDemo for button ids, e.g. "Sauce Labs Backpack" -> "sauce-labs-backpack". */
    protected static String slug(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }

    // ---- shared cart header ----

    public void openCart() {
        click(cartLink);
    }

    /** Waits for the badge to show the expected number (or to disappear when expected is 0). */
    public boolean cartBadgeShowsCount(int expected) {
        try {
            if (expected == 0) {
                return Boolean.TRUE.equals(wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge)));
            }
            return Boolean.TRUE.equals(wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(expected))));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Current badge number without waiting (0 when the badge is not shown). Useful in failure messages. */
    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText().trim());
    }
}

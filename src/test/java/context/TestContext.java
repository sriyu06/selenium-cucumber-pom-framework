package context;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utilities.DriverManager;

/**
 * Created fresh by PicoContainer for every scenario and injected into each step-definition class,
 * so all steps in one scenario share the same page objects (and therefore the same browser).
 * Pages are created lazily, after the @Before hook has started the browser.
 */
public class TestContext {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver());
        }
        return loginPage;
    }

    public InventoryPage inventoryPage() {
        if (inventoryPage == null) {
            inventoryPage = new InventoryPage(driver());
        }
        return inventoryPage;
    }

    public CartPage cartPage() {
        if (cartPage == null) {
            cartPage = new CartPage(driver());
        }
        return cartPage;
    }

    public CheckoutPage checkoutPage() {
        if (checkoutPage == null) {
            checkoutPage = new CheckoutPage(driver());
        }
        return checkoutPage;
    }
}

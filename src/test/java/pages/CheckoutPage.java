package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Covers the three checkout screens: information (step one), overview (step two) and complete.
 */
public class CheckoutPage extends BasePage {

    // Step one: your information
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Step two: overview
    private final By overviewNames = By.className("inventory_item_name");
    private final By overviewPrices = By.className("inventory_item_price");
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    // Complete
    private final By confirmationHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ---- step one ----
    public boolean isInformationStepDisplayed() {
        return waitForUrlContains("checkout-step-one.html");
    }

    public void enterInformation(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    // ---- step two ----
    public boolean isOverviewDisplayed() {
        return waitForUrlContains("checkout-step-two.html");
    }

    public List<String> getOverviewItemNames() {
        return driver.findElements(overviewNames).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public double getSumOfItemPrices() {
        return driver.findElements(overviewPrices).stream().mapToDouble(e -> parsePrice(e.getText())).sum();
    }

    public double getItemTotal() {
        return parsePrice(getText(subtotalLabel));
    }

    public double getTax() {
        return parsePrice(getText(taxLabel));
    }

    public double getTotal() {
        return parsePrice(getText(totalLabel));
    }

    public void finish() {
        click(finishButton);
    }

    // ---- complete ----
    public boolean isOrderComplete() {
        return waitForUrlContains("checkout-complete.html");
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }
}

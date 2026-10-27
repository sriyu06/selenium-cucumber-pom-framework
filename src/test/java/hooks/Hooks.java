package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.DriverManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Scenario lifecycle: a fresh browser before every scenario, and a screenshot
 * (on failure) plus a clean browser shutdown after every scenario.
 */
public class Hooks {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");

    @Before
    public void setUp() {
        DriverManager.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                captureScreenshot(scenario);
            }
        } finally {
            DriverManager.quitDriver(); // always runs, even if the screenshot fails
        }
    }

    private void captureScreenshot(Scenario scenario) {
        byte[] png = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

        // 1) Embed in the Cucumber HTML report
        scenario.attach(png, "image/png", scenario.getName());

        // 2) Also save a file under target/screenshots
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Files.write(SCREENSHOT_DIR.resolve(safeName + "_" + time + ".png"), png);
        } catch (IOException e) {
            System.err.println("Could not save screenshot file: " + e.getMessage());
        }
    }
}

package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * The single entry point. AbstractTestNGCucumberTests exposes every Cucumber scenario
 * as a TestNG test (through a @DataProvider), so Maven Surefire can run them.
 *
 * Select scenarios from the command line: -Dcucumber.filter.tags="@smoke"
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // Scenarios run sequentially. To run in parallel later, override scenarios()
    // with @DataProvider(parallel = true) - the ThreadLocal driver is already parallel-safe.
}

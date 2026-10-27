# Selenium + Cucumber + TestNG + POM Automation Framework

A UI test-automation framework for the [SauceDemo](https://www.saucedemo.com/) demo shop, built with Java, Selenium WebDriver, Cucumber BDD, TestNG and the Page Object Model. It is a learning / portfolio project.

> **Status:** Add your own verified results in the "Sample execution results" section after you run the suite on your machine.

## Objectives

- Practise a clean separation of test intent (Gherkin), test glue (step definitions) and page interaction (page objects).
- Use explicit waits, readable assertions and failure screenshots.
- Run the whole suite, or only `@smoke` / `@regression` scenarios, from Maven.

## Technology stack

| Tool | Version | Role |
|------|---------|------|
| Java | 17+ | Language |
| Selenium WebDriver | 4.25.0 | Browser automation (Selenium Manager downloads the browser driver automatically) |
| Cucumber (java, testng, picocontainer) | 7.20.1 | BDD, Gherkin, dependency injection |
| TestNG | 7.10.2 | Test engine and assertions |
| Maven Surefire | 3.2.5 | Runs the tests from `mvn test` |
| Maven | 3.8+ | Build and dependencies |

## Folder structure

```
selenium-cucumber-pom-framework/
├── pom.xml                         Dependencies and plugins
├── README.md
├── .gitignore
├── docs/INTERVIEW_PREP.md          Interview questions based on this project
└── src/test/
    ├── java/
    │   ├── pages/                  Page objects (locators + page actions)
    │   ├── stepdefinitions/        Cucumber glue code, delegates to pages
    │   ├── runners/                TestNG-Cucumber runner (single entry point)
    │   ├── hooks/                  @Before / @After (browser lifecycle, screenshots)
    │   ├── context/                TestContext shared by steps within one scenario
    │   └── utilities/              ConfigReader, DriverManager
    └── resources/
        ├── features/               Gherkin feature files
        ├── config.properties       URL, browser, timeouts, public demo credentials
        └── cucumber.properties     Cucumber settings
```

## Prerequisites

1. JDK 17 or newer (`java -version`)
2. Maven 3.8+ (`mvn -version`)
3. Google Chrome (default). Firefox or Edge also work.
4. Internet access (the tests hit saucedemo.com; Selenium Manager downloads the driver on first run)

## Configuration

Edit `src/test/resources/config.properties`, or override any value on the command line:

| Key | Default | Meaning |
|-----|---------|---------|
| `base.url` | https://www.saucedemo.com/ | Application URL |
| `browser` | chrome | `chrome`, `firefox` or `edge` |
| `headless` | false | Run without a visible browser |
| `explicit.wait.seconds` | 10 | Explicit wait timeout |
| `valid.username` / `valid.password` | standard_user / secret_sauce | **Public** demo credentials shown on the SauceDemo login page. Not real credentials. |

## Running the tests

```bash
# All tests
mvn clean test

# Smoke only
mvn clean test -Dcucumber.filter.tags="@smoke"

# Regression only
mvn clean test -Dcucumber.filter.tags="@regression"

# Headless Firefox
mvn clean test -Dbrowser=firefox -Dheadless=true

# One feature or a tag combination
mvn clean test -Dcucumber.filter.tags="@regression and not @smoke"
```

## Reports and screenshots

| Output | Location |
|--------|----------|
| Cucumber HTML report | `target/cucumber-reports/cucumber.html` |
| Cucumber JSON report | `target/cucumber-reports/cucumber.json` |
| Failure screenshots | `target/screenshots/` (also embedded in the HTML report) |
| Surefire results | `target/surefire-reports/` |

All of `target/` is git-ignored.

## Test scenario inventory

| Feature | Scenario | Tags |
|---------|----------|------|
| Login | Successful login | @smoke @regression |
| Login | Failed login outline: invalid password, invalid username, empty username, empty password, locked-out user (with error message checks) | @regression |
| Product listing | Inventory page displayed after login | @smoke @regression |
| Product listing | 6 products with name, description, price, image | @regression |
| Product listing | Sort outline: Name A-Z, Z-A, Price low-high, high-low | @regression |
| Cart | Add product, badge shows 1 | @smoke @regression |
| Cart | Cart shows name, price, quantity | @regression |
| Cart | Badge counts multiple products | @regression |
| Cart | Remove from inventory page | @regression |
| Cart | Remove from cart page | @smoke @regression |
| Checkout | Required-field outline: first name, last name, postal code | @regression |
| Checkout | Overview shows product and consistent totals | @regression |
| Checkout | Complete purchase, confirmation shown | @smoke @regression |

## Architecture

```
Feature file (Gherkin)  ->  Step definition  ->  Page object  ->  Selenium WebDriver  ->  Browser
        ^                        |                    |
        |                  TestContext (per scenario, injected by PicoContainer)
   TestRunner (TestNG + Cucumber) <- Maven Surefire
   Hooks: @Before starts the driver, @After screenshots on failure and quits
```

- **Feature files** describe behaviour in business language only.
- **Step definitions** contain no locators; they call page methods and make assertions.
- **Page objects** hold locators and page actions; `BasePage` holds wait helpers and the cart header shared by all pages.
- **DriverManager** stores the WebDriver in a `ThreadLocal`, so it is safe for future parallel runs.
- **TestRunner** extends `AbstractTestNGCucumberTests`; each scenario becomes a TestNG test and Surefire runs this single class.
- **Reliability:** explicit waits only (no `Thread.sleep`, no implicit wait), no retry mechanism.

## Sample execution results

Not yet recorded. After running `mvn clean test` on your machine, paste the real console summary and the date here, for example:

```
Tests run: <n>, Failures: <n>, Errors: <n>, Skipped: <n>
```

## Known limitations and future improvements

- Assertions on SauceDemo error texts depend on the site's current wording.
- Scenarios run sequentially (parallel execution is prepared but not enabled).
- Only UI tests; no API layer.
- Ideas: GitHub Actions CI workflow, Allure or Extent reporting, parallel execution, data-driven users, API tests with REST Assured.

## License

For learning and portfolio use.

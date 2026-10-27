@regression
Feature: Checkout
  As a logged-in shopper
  I want to complete a purchase

  Background:
    Given the user is logged in with valid credentials
    And the user adds "Sauce Labs Backpack" to the cart
    And the user opens the cart
    And the user proceeds to checkout

  Scenario Outline: Required checkout fields are validated - <case>
    When the user enters first name "<first>", last name "<last>" and postal code "<postal>"
    And the user continues to the order overview
    Then the checkout error message should be "<message>"

    Examples:
      | case                | first | last | postal | message                        |
      | missing first name  |       | Doe  | 500081 | Error: First Name is required  |
      | missing last name   | John  |      | 500081 | Error: Last Name is required   |
      | missing postal code | John  | Doe  |        | Error: Postal Code is required |

  Scenario: Checkout overview shows the selected product and correct totals
    When the user enters first name "John", last name "Doe" and postal code "500081"
    And the user continues to the order overview
    Then the order overview should be displayed
    And the order overview should list "Sauce Labs Backpack"
    And the order totals should be calculated correctly

  @smoke
  Scenario: Complete a valid purchase
    When the user enters first name "John", last name "Doe" and postal code "500081"
    And the user continues to the order overview
    And the user finishes the order
    Then the order confirmation "Thank you for your order!" should be displayed

@regression
Feature: Shopping cart
  As a logged-in shopper
  I want to manage items in my cart

  Background:
    Given the user is logged in with valid credentials

  @smoke
  Scenario: Add a product to the cart
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1

  Scenario: Cart shows the correct product name, price and quantity
    When the user adds "Sauce Labs Backpack" to the cart
    And the user opens the cart
    Then the cart should contain "Sauce Labs Backpack"
    And the cart item "Sauce Labs Backpack" should have price "$29.99" and quantity 1

  Scenario: Cart badge counts multiple products
    When the user adds "Sauce Labs Backpack" to the cart
    And the user adds "Sauce Labs Bike Light" to the cart
    Then the cart badge should show 2

  Scenario: Remove a product from the inventory page
    When the user adds "Sauce Labs Backpack" to the cart
    And the user removes "Sauce Labs Backpack" from the inventory page
    Then the cart badge should not be displayed

  @smoke
  Scenario: Remove a product from the cart page
    When the user adds "Sauce Labs Backpack" to the cart
    And the user opens the cart
    And the user removes "Sauce Labs Backpack" from the cart page
    Then the cart should be empty
    And the cart badge should not be displayed

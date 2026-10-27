@regression
Feature: Product listing
  As a logged-in shopper
  I want to see and sort the available products

  Background:
    Given the user is logged in with valid credentials

  @smoke
  Scenario: Inventory page is displayed after login
    Then the inventory page should be displayed

  Scenario: Products and their details are displayed
    Then the inventory should list 6 products
    And every product should show a name, description, price and image

  Scenario Outline: Sort products by <option>
    When the user sorts the products by "<option>"
    Then the products should be ordered by "<option>"

    Examples:
      | option              |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |

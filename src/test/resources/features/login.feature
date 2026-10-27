@regression
Feature: Login
  As a shopper
  I want to log in to SauceDemo
  So that I can browse and buy products

  Background:
    Given the user is on the login page

  @smoke
  Scenario: Successful login with valid credentials
    When the user logs in with valid credentials
    Then the inventory page should be displayed

  Scenario Outline: Failed login shows the correct error message - <case>
    When the user logs in with username "<username>" and password "<password>"
    Then the login error message should be "<message>"

    Examples:
      | case             | username        | password     | message                                                                   |
      | invalid password | standard_user   | wrong_pass   | Epic sadface: Username and password do not match any user in this service |
      | invalid username | unknown_user    | secret_sauce | Epic sadface: Username and password do not match any user in this service |
      | empty username   |                 | secret_sauce | Epic sadface: Username is required                                        |
      | empty password   | standard_user   |              | Epic sadface: Password is required                                        |
      | locked out user  | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |

@phase2 @module_account_payable @navigation
Feature: Account Payable Navigation

  Background:
    Given user is logged in

  Scenario: Navigate to Account Payable module from Home screen
    When user navigates to account payable module
    Then Account Payable module should be opened

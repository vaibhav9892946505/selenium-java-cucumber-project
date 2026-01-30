@smoke @regression @login
Feature: Login Feature

  @phase1 @module_login @sanity @severity=critical @priority=P1
  Scenario: Verify user can login using excel data
    Given user is on login page
    When user logs in using excel data
    Then all login results should be verified

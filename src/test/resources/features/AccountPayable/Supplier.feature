@phase1 @supplier @create
Feature: Supplier creation with payment variations

  @supplier @basic
  Scenario: Create supplier without BACS or Payee
    When user creates supplier with payment option "NONE"
    Then supplier should be created successfully

  @supplier @bacs
  Scenario: Create supplier with BACS payable only
    When user creates supplier with payment option "BACS_ONLY"
    Then supplier should be created successfully

  @supplier @full
  Scenario: Create supplier with BACS, Payee and Email remittance
    When user creates supplier with payment option "FULL"
    Then supplier should be created successfully

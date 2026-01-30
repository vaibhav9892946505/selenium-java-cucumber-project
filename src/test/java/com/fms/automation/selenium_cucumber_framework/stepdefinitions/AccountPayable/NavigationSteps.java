package com.fms.automation.selenium_cucumber_framework.stepdefinitions.AccountPayable;

import com.fms.automation.selenium_cucumber_framework.flows.HomeFlow;
import com.fms.automation.selenium_cucumber_framework.reports.ExtentTestManager;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NavigationSteps {

    HomeFlow homeFlow = new HomeFlow();

    @When("user navigates to account payable module")
    public void user_navigates_to_account_payable_module() {
        homeFlow.navigateToModuleUsingExcel("Navigation");
    }

    @Then("Account Payable module should be opened")
    public void account_payable_module_should_be_opened() {
        ExtentTestManager.getTest().info("Account Payable module opened successfully");
    }
}

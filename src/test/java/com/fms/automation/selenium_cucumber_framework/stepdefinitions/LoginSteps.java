package com.fms.automation.selenium_cucumber_framework.stepdefinitions;

import com.fms.automation.selenium_cucumber_framework.flows.LoginFlow;
import com.fms.automation.selenium_cucumber_framework.reports.ExtentTestManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private LoginFlow loginFlow;

    @Given("user is on login page")
    public void user_is_on_login_page() {
        loginFlow = new LoginFlow();
        ExtentTestManager.getTest().info("Navigated to Login Page");
    }

    @When("user logs in using excel data")
    public void user_logs_in_using_excel_data() {
        loginFlow.executeLoginUsingExcel();
    }

    @Then("all login results should be verified")
    public void all_login_results_should_be_verified() {
        loginFlow.verifyLoginExecutionCompleted();
    }
}

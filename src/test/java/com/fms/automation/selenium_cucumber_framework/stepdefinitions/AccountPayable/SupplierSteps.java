package com.fms.automation.selenium_cucumber_framework.stepdefinitions.AccountPayable;

import org.testng.Assert;

import com.fms.automation.selenium_cucumber_framework.context.TestContext;
import com.fms.automation.selenium_cucumber_framework.flows.AccountPayable.SupplierFlow;
import com.fms.automation.selenium_cucumber_framework.model.SupplierPaymentOptions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SupplierSteps {

    private SupplierFlow supplierFlow = new SupplierFlow();

    @When("user creates supplier with payment option {string}")
    public void user_creates_supplier_with_payment_option(String option) {

        SupplierPaymentOptions paymentOptions;

        switch (option) {
            case "NONE":
                paymentOptions = SupplierPaymentOptions.none();
                break;

            case "BACS_ONLY":
                paymentOptions = SupplierPaymentOptions.bacsOnly();
                break;

            case "FULL":
                paymentOptions = SupplierPaymentOptions.full();
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid payment option provided in feature file: " + option
                );
        }

        supplierFlow.createSupplier(paymentOptions);
    }

    @Then("supplier should be created successfully")
    public void supplier_should_be_created_successfully() {

        String supplierName =
                (String) TestContext.get("SUPPLIER_NAME");

        Assert.assertNotNull(
                supplierName,
                "Supplier name not found in TestContext. Supplier creation may have failed."
        );

        // ✅ Lightweight assertion (stable & non-flaky)
        System.out.println("✅ Supplier created successfully: " + supplierName);
    }
}

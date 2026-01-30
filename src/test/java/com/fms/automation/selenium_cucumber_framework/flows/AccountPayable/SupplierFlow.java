package com.fms.automation.selenium_cucumber_framework.flows.AccountPayable;

import com.fms.automation.selenium_cucumber_framework.context.TestContext;
import com.fms.automation.selenium_cucumber_framework.factory.DriverFactory;
import com.fms.automation.selenium_cucumber_framework.model.SupplierPaymentOptions;
import com.fms.automation.selenium_cucumber_framework.pageobjects.AccountPayable.SupplierDetailsPage;
import com.fms.automation.selenium_cucumber_framework.pageobjects.AccountPayable.SupplierLandingPage;
import com.fms.automation.selenium_cucumber_framework.pageobjects.AccountPayable.SupplierListingPage;
import com.fms.automation.selenium_cucumber_framework.pageobjects.HomePage;

public class SupplierFlow {

    private HomePage homePage;
    private SupplierLandingPage supplierLandingPage;
    private SupplierListingPage listingPage;
    private SupplierDetailsPage detailsPage;

    public SupplierFlow() {
        homePage = new HomePage(DriverFactory.getDriver());
        supplierLandingPage = new SupplierLandingPage(DriverFactory.getDriver());
        listingPage = new SupplierListingPage(DriverFactory.getDriver());
        detailsPage = new SupplierDetailsPage(DriverFactory.getDriver());
    }

    public void createSupplier(SupplierPaymentOptions options) {

        String supplierName = "SUP-AUTO-" + System.currentTimeMillis();

        // =====================================================
        // STEP 1: Navigate to Accounts Payable → Supplier
        // =====================================================
        homePage.navigateToModule("Accounts Payable");
        supplierLandingPage.openSupplierTile();
        listingPage.clickAddSupplier();

        // =====================================================
        // STEP 2: BASIC + ADDITIONAL (MANDATORY ONLY)
        // =====================================================
        detailsPage.fillBasicTab(
                supplierName,
                "Automation Address Line 1"
        );

        detailsPage.fillAdditionalTabMandatoryFields();

        // =====================================================
        // STEP 3: SINGLE SAVE (ALL RETRIES INSIDE PAGE OBJECT)
        // =====================================================
        detailsPage.saveSupplier();

        // =====================================================
        // STEP 4: HARD ASSERT — WAS IT REALLY SAVED?
        // =====================================================
        if (!detailsPage.isSupplierActuallySaved()) {
            throw new AssertionError(
                "❌ Supplier SAVE FAILED. " +
                "Save button was clicked but supplier was NOT persisted. " +
                "This is an application issue (silent validation failure)."
            );
        }

        // =====================================================
        // STEP 5: CONFIRM CREATION UI SIGNAL
        // =====================================================
        detailsPage.waitForSupplierCreation();

        // =====================================================
        // STEP 6: GO BACK TO LISTING
        // =====================================================
        detailsPage.goBackToSupplierListing();

        // =====================================================
        // STEP 7: SEARCH & VERIFY
        // =====================================================
        listingPage.searchSupplier(supplierName);
        listingPage.verifySupplierPresent(supplierName);

        // =====================================================
        // STORE CONTEXT FOR FUTURE FLOWS
        // =====================================================
        TestContext.set("SUPPLIER_NAME", supplierName);

        System.out.println("✅ Supplier created successfully: " + supplierName);
    }
}

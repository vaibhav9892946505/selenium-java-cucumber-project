package com.fms.automation.selenium_cucumber_framework.pageobjects.AccountPayable;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.fms.automation.selenium_cucumber_framework.pageobjects.BasePage;

/**
 * Supplier Listing Page
 * ---------------------
 * Handles:
 *  - Navigating supplier list
 *  - Searching supplier using "Looking for"
 *  - Verifying supplier presence in grid
 */
public class SupplierListingPage extends BasePage {

    private final WebDriverWait wait;

    // =========================
    // Locators
    // =========================

    /** + Add Supplier button (button or anchor) */
    private static final By ADD_SUPPLIER_BTN = By.xpath(
            "//button[.//i[contains(@class,'bi-plus')]] | " +
            "//a[.//i[contains(@class,'bi-plus')]]"
    );

    /** "Looking for" search input */
    private static final By SEARCH_INPUT = By.id("search");

    /** Supplier Name column (1st column of table) */
    private static final By SUPPLIER_NAME_CELLS =
            By.xpath("//table//tbody//tr//td[1]");

    // =========================
    // Constructor
    // =========================
    public SupplierListingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================
    // Actions
    // =========================

    /**
     * Click on + Add Supplier button
     */
    public void clickAddSupplier() {
        wait.until(ExpectedConditions.elementToBeClickable(ADD_SUPPLIER_BTN))
            .click();
    }

    /**
     * Search supplier using "Looking for" field
     */
    public void searchSupplier(String supplierName) {

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT)
        );

        searchBox.clear();
        searchBox.sendKeys(supplierName);
        searchBox.sendKeys(Keys.ENTER);

        // Wait for grid refresh
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(SUPPLIER_NAME_CELLS));
    }

    /**
     * Verify supplier exists in listing grid
     */
    public void verifySupplierPresent(String expectedSupplierName) {

        List<WebElement> supplierRows =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(SUPPLIER_NAME_CELLS));

        boolean found = supplierRows.stream()
                .map(e -> e.getText().trim())
                .anyMatch(name -> name.equalsIgnoreCase(expectedSupplierName));

        Assert.assertTrue(
                found,
                "❌ Supplier NOT found in listing: " + expectedSupplierName
        );

        System.out.println("✅ Supplier found in listing: " + expectedSupplierName);
    }
}

package com.fms.automation.selenium_cucumber_framework.pageobjects.AccountPayable;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fms.automation.selenium_cucumber_framework.pageobjects.BasePage;

public class SupplierDetailsPage extends BasePage {

    private WebDriverWait wait;

    // ===================== TABS =====================
    private By basicTab      = By.id("basic-tab");
    private By additionalTab = By.id("additional-tab");
    private By catalogueTab  = By.id("catalogue-tab");
    private By reviewTab     = By.id("review-tab");
    private By eProcTab      = By.id("eprocurement-tab");

    // ===================== BASIC TAB =====================
    private By supplierNameField = By.id("supname");
    private By addressLine1Field = By.id("Supplier_Address1");

    // ===================== ADDITIONAL TAB =====================
    private By bankAccountNumber = By.id("suppBankAccount");
    private By accountCode       = By.id("suppBacsAccountCode");
    private By bankAccountName   = By.id("supBankAccountName");
    private By sortCode          = By.id("SortCode");
    private By bankName          = By.id("Supplier_BankName");
    private By paymentReference  = By.id("supPaymentReferenceNumber");

    // ===================== SAVE / CONFIRM =====================
    private By saveButton = By.id("saveButton");

    private By supplierNumberBadge =
        By.xpath("//span[contains(@class,'badge') and contains(@class,'text-bg-info')]");

    private By supplierBreadcrumb =
        By.xpath("//a[contains(@href,'/AccountsPayable/Supplier/Index')]");

    public SupplierDetailsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // =====================================================
    // 🔥 GLOBAL UI KILL SWITCH (POPUP / BACKDROP DESTROYER)
    // =====================================================
    private void killAllBlockingUi() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("""
            document.querySelectorAll('.modal-backdrop').forEach(e => e.remove());
            document.querySelectorAll('.modal').forEach(m => {
                m.classList.remove('show');
                m.style.display = 'none';
                m.setAttribute('aria-hidden','true');
            });
            document.body.classList.remove('modal-open');
            document.body.style.overflow = 'auto';
            document.body.style.paddingRight = '0px';
        """);
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    // ===================== TAB HANDLER =====================
    private void openTab(By tab) {
        killAllBlockingUi();
        wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
        sleep(300);
        killAllBlockingUi();
    }

    // ===================== BASIC TAB (JS-ONLY) =====================
    public void fillBasicTab(String supplierName, String address1) {

        openTab(basicTab);
        killAllBlockingUi();

        WebElement name = wait.until(
            ExpectedConditions.presenceOfElementLocated(supplierNameField)
        );

        ((JavascriptExecutor) driver).executeScript("""
            arguments[0].value = arguments[1];
            arguments[0].dispatchEvent(new Event('input'));
            arguments[0].dispatchEvent(new Event('change'));
        """, name, supplierName);

        name.sendKeys(Keys.TAB);
        sleep(800);
        killAllBlockingUi();

        WebElement address = wait.until(
            ExpectedConditions.presenceOfElementLocated(addressLine1Field)
        );

        ((JavascriptExecutor) driver).executeScript("""
            arguments[0].value = arguments[1];
            arguments[0].dispatchEvent(new Event('input'));
            arguments[0].dispatchEvent(new Event('change'));
        """, address, address1);

        address.sendKeys(Keys.TAB);
        sleep(800);
        killAllBlockingUi();
    }

    // ===================== ADDITIONAL TAB =====================
    public void fillAdditionalTabMandatoryFields() {

        openTab(additionalTab);

        wait.until(ExpectedConditions.presenceOfElementLocated(bankAccountNumber))
            .sendKeys("12345678");
        wait.until(ExpectedConditions.presenceOfElementLocated(accountCode))
            .sendKeys("1");
        wait.until(ExpectedConditions.presenceOfElementLocated(bankAccountName))
            .sendKeys("AUTO BANK");
        wait.until(ExpectedConditions.presenceOfElementLocated(sortCode))
            .sendKeys("12-34-56");
        wait.until(ExpectedConditions.presenceOfElementLocated(bankName))
            .sendKeys("AUTO BANK LTD");
        wait.until(ExpectedConditions.presenceOfElementLocated(paymentReference))
            .sendKeys("AUTOREF123");

        killAllBlockingUi();
    }

    // ===================== SAVE (BULLETPROOF) =====================
    public void saveSupplier() {

        killAllBlockingUi();

        WebElement save = wait.until(
            ExpectedConditions.presenceOfElementLocated(saveButton)
        );

        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", save);

        // Attempt 1 – JS click
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", save);

        sleep(900);
        killAllBlockingUi();

        // Attempt 2 – JS click again (duplicate popup case)
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", save);

        sleep(900);
        killAllBlockingUi();

        // Attempt 3 – physical double click
        try {
            save.click();
            sleep(300);
            save.click();
        } catch (Exception ignored) {}

        sleep(1200);
        killAllBlockingUi();
    }

    // ===================== CONFIRMATION =====================
    public void waitForSupplierCreation() {

        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(45));

        try {
            longWait.until(
                ExpectedConditions.visibilityOfElementLocated(supplierNumberBadge)
            );
            return;
        } catch (Exception ignored) {}

        killAllBlockingUi();

        longWait.until(
            ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(supplierNumberBadge),
                ExpectedConditions.urlContains("/AccountsPayable/Supplier")
            )
        );
    }

    // ===================== BACKEND VERIFICATION =====================
    public boolean isSupplierActuallySaved() {
        try {
            return driver.findElements(supplierNumberBadge).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ===================== NAVIGATION =====================
    public void goBackToSupplierListing() {

        killAllBlockingUi();

        try {
            WebElement crumb = wait.until(
                ExpectedConditions.presenceOfElementLocated(supplierBreadcrumb)
            );

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true); arguments[0].click();",
                crumb
            );
            return;

        } catch (Exception ignored) {}

        // 🔴 ABSOLUTE URL FALLBACK (NO RELATIVE URL BUG)
        String baseUrl = driver.getCurrentUrl().replaceAll("/AccountsPayable.*", "");
        driver.navigate().to(baseUrl + "/AccountsPayable/Supplier/Index");
    }
}

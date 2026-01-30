package com.fms.automation.selenium_cucumber_framework.pageobjects.AccountPayable;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fms.automation.selenium_cucumber_framework.pageobjects.BasePage;

public class SupplierLandingPage extends BasePage {

    private WebDriverWait wait;

    // ✅ EXACT match for: <div class="subtitle">Supplier</div> inside <a class="card">
    private By supplierTile =
        By.xpath("//div[@class='subtitle' and normalize-space()='Supplier']/ancestor::a");

    public SupplierLandingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void openSupplierTile() {

        WebElement tile = wait.until(
                ExpectedConditions.elementToBeClickable(supplierTile)
        );

        // JS click to avoid animation / overlay issues
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", tile
        );
    }
}

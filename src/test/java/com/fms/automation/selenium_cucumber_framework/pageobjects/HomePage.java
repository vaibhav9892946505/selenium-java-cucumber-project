package com.fms.automation.selenium_cucumber_framework.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Dynamically navigates to a module based on visible module name
     * Example: "Accounts Payable", "General Ledger"
     */
    public void navigateToModule(String moduleName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // ✅ Locate the clickable <a class="card"> containing module subtitle
        By moduleCard = By.xpath(
            "//a[contains(@class,'card')]//div[@class='subtitle' and normalize-space()='" 
            + moduleName + "']/ancestor::a"
        );

        WebElement card = wait.until(
                ExpectedConditions.elementToBeClickable(moduleCard)
        );

        card.click();
    }


}

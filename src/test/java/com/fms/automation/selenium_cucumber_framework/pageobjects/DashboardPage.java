package com.fms.automation.selenium_cucumber_framework.pageobjects;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage extends BasePage {

    @FindBy(id = "userDropdown")
    private WebElement profileIcon;

    @FindBy(xpath = "//button[@class='dropdown-item']")
    private WebElement logout;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // ✅ Verifies user is logged in
    public boolean isUserLoggedIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(profileIcon));
            return profileIcon.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Defensive logout for data-driven execution
    public void logout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for profile icon and open menu
            wait.until(ExpectedConditions.visibilityOf(profileIcon));
            profileIcon.click();

            // Wait for logout option
            wait.until(ExpectedConditions.visibilityOf(logout));
            wait.until(ExpectedConditions.elementToBeClickable(logout));
            logout.click();

        } catch (TimeoutException e) {
            System.out.println("⚠ Logout not performed – logout option not visible");
        }
    }
}

package com.fms.automation.selenium_cucumber_framework.flows;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.fms.automation.selenium_cucumber_framework.factory.DriverFactory;
import com.fms.automation.selenium_cucumber_framework.pageobjects.DashboardPage;
import com.fms.automation.selenium_cucumber_framework.pageobjects.LoginPage;
import com.fms.automation.selenium_cucumber_framework.reports.ExtentTestManager;
import com.fms.automation.selenium_cucumber_framework.utils.ExcelUtil;
import com.fms.automation.selenium_cucumber_framework.utils.TestDataConstants;

public class LoginFlow {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    public LoginFlow() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        dashboardPage = new DashboardPage(DriverFactory.getDriver());
    }

    public void executeLoginUsingExcel() {

        // ✅ Load Excel file ONCE
        ExcelUtil.setExcelFile("LoginData.xlsx");

        List<Map<String, String>> testData =
                ExcelUtil.getTestData(TestDataConstants.LOGIN_SHEET);

        for (Map<String, String> row : testData) {

            String testCaseId = row.get("TestCaseID");
            String username   = row.get("username");
            String password   = row.get("password");
            String expected   = row.get("expected");

            // ✅ SAFE parent fetch
            ExtentTest parent = ExtentTestManager.getTest();

            // ✅ SAFE child creation
            ExtentTest child = (parent != null)
                    ? parent.createNode("TestCase: " + testCaseId)
                    : null;

            if (child != null) {
                child.info("Username: " + username);
            }

            // 🔐 Perform login
            loginPage.login(username, password);

            boolean isLoggedIn = dashboardPage.isUserLoggedIn();

            if ("success".equalsIgnoreCase(expected)) {

                if (child != null && !isLoggedIn) {
                    child.fail("❌ Expected SUCCESS but login failed");
                }

                Assert.assertTrue(
                        isLoggedIn,
                        "Expected login success but failed for " + testCaseId
                );

                if (child != null) {
                    child.pass("✅ Login successful");
                }

                // Logout before next iteration
                dashboardPage.logout();

            } else {

                if (child != null && isLoggedIn) {
                    child.fail("❌ Expected FAILURE but login succeeded");
                }

                Assert.assertFalse(
                        isLoggedIn,
                        "Expected login failure but passed for " + testCaseId
                );

                if (child != null) {
                    child.pass("✅ Negative login scenario passed");
                }

                // Reset state
                DriverFactory.getDriver().navigate().refresh();
            }
        }
    }

    public void verifyLoginExecutionCompleted() {
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.info("All Excel-driven login test cases executed successfully");
        }
        
        
    }
    public void loginWithValidUser() {

        // 👇 pick ONLY FIRST ROW (valid creds)
        ExcelUtil.setExcelFile("LoginData.xlsx");

        Map<String, String> row =
                ExcelUtil.getTestData(TestDataConstants.LOGIN_SHEET).get(0);

        String username = row.get("username");
        String password = row.get("password");

        loginPage.login(username, password);

        Assert.assertTrue(
                dashboardPage.isUserLoggedIn(),
                "Login failed with valid credentials"
        );
    }

}

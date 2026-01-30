package com.fms.automation.selenium_cucumber_framework.hooks;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.fms.automation.selenium_cucumber_framework.factory.DriverFactory;
import com.fms.automation.selenium_cucumber_framework.flows.LoginFlow;
import com.fms.automation.selenium_cucumber_framework.reports.ExecutionSummary;
import com.fms.automation.selenium_cucumber_framework.reports.ExtentManager;
import com.fms.automation.selenium_cucumber_framework.reports.ExtentTestManager;
import com.fms.automation.selenium_cucumber_framework.utils.ConfigReader;
import com.fms.automation.selenium_cucumber_framework.utils.ScreenshotUtil;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class AppHooks {

    private Properties prop;
    private static ExtentReports extent;

    // ===================== BEFORE HOOK =====================
    @Before
    public void setUp(Scenario scenario) {

        prop = ConfigReader.loadProperties();

        // 1️⃣ Start browser
        DriverFactory.initDriver(prop.getProperty("browser"));

        // 2️⃣ Launch application
        DriverFactory.getDriver().get(prop.getProperty("url"));

        // 3️⃣ Initialize Extent ONCE
        if (extent == null) {
            extent = ExtentManager.getExtentReports();
        }

        // 4️⃣ Create Extent test for THIS scenario (🔥 MUST BE BEFORE LOGIN)
        ExtentTest test = extent.createTest(scenario.getName());
        ExtentTestManager.setTest(test);

        // 5️⃣ Assign tags & summary counters
        Collection<String> tags = scenario.getSourceTagNames();
        for (String tag : tags) {

            String cleanTag = tag.replace("@", "").toLowerCase();

            if (cleanTag.startsWith("phase")) {
                ExecutionSummary.incrementPhase(cleanTag);
            } else if (cleanTag.startsWith("module")) {
                ExecutionSummary.incrementModule(cleanTag);
            } else if (cleanTag.equals("sanity") || cleanTag.equals("regression")) {
                ExecutionSummary.incrementRunType(cleanTag);
            }

            test.assignCategory(cleanTag);
        }

        // 6️⃣ Perform LOGIN (child logging now works safely)
        
        LoginFlow loginFlow = new LoginFlow();
        loginFlow.loginWithValidUser();

    }

    // ===================== AFTER HOOK =====================
    @After
    public void tearDown(Scenario scenario) {

        try {
            ExtentTest test = ExtentTestManager.getTest();

            if (test != null) {
                if (scenario.isFailed()) {

                    String screenshotPath =
                            ScreenshotUtil.captureScreenshot(
                                    DriverFactory.getDriver(),
                                    scenario.getName());

                    String relativePath =
                            "./screenshots/" + new File(screenshotPath).getName();

                    test.fail("Scenario Failed ❌")
                        .addScreenCaptureFromPath(relativePath);

                } else {
                    test.pass("Scenario Passed ✅");
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error during Extent reporting: " + e.getMessage());
        } finally {

            if (DriverFactory.getDriver() != null) {
                DriverFactory.quitDriver();
            }

            if (extent != null) {
                extent.flush();
            }

            ExtentTestManager.unload();
        }
    }
}

package com.fms.automation.selenium_cucumber_framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.fms.automation.selenium_cucumber_framework.stepdefinitions",
                "com.fms.automation.selenium_cucumber_framework.hooks"
        },
        tags = "@supplier and @basic",
        plugin = {
                "pretty",
                "html:target/supplier-report.html"
        },
        monochrome = true
)
public class SupplierTestRunner extends AbstractTestNGCucumberTests {
}

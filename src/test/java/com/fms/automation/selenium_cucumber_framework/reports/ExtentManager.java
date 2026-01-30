package com.fms.automation.selenium_cucumber_framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports/AutomationReport.html");
            spark.config().setReportName("FMS Automation Test Report");
            spark.config().setDocumentTitle("FMS Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Project", "FMS Automation");
            extent.setSystemInfo("Environment", "UAT");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Executed By", "Vaibhav Yadav");
        }
        return extent;
    }

    // ✅ NEW METHOD: Add Execution Summary to Report
    public static void addExecutionSummary(ExtentReports extent) {

        if (ExecutionSummary.isSummaryGenerated()) {
            return; // prevent duplicates
        }

        ExtentTest summary = extent.createTest("📊 Execution Summary");

        summary.info("=========== Phase-wise Execution ===========");
        ExecutionSummary.getPhaseCount().forEach((phase, count) ->
                summary.info(phase + " : " + count + " scenarios"));

        summary.info("=========== Module-wise Execution ===========");
        ExecutionSummary.getModuleCount().forEach((module, count) ->
                summary.info(module + " : " + count + " scenarios"));

        summary.info("=========== Run Type Execution ===========");
        ExecutionSummary.getRunTypeCount().forEach((type, count) ->
                summary.info(type + " : " + count + " scenarios"));

        ExecutionSummary.markSummaryGenerated();
    }
}

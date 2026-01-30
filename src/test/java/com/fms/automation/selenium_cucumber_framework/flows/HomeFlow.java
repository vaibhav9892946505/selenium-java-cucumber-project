package com.fms.automation.selenium_cucumber_framework.flows;

import java.util.List;
import java.util.Map;

import com.fms.automation.selenium_cucumber_framework.factory.DriverFactory;
import com.fms.automation.selenium_cucumber_framework.pageobjects.HomePage;
import com.fms.automation.selenium_cucumber_framework.utils.ExcelUtil;

public class HomeFlow {

    public void navigateToModuleUsingExcel(String sheetName) {

        // 🔑 Explicitly set the module Excel file
        ExcelUtil.setExcelFile("AccountPayable_Data.xlsx");

        HomePage homePage = new HomePage(DriverFactory.getDriver());

        List<Map<String, String>> data = ExcelUtil.getTestData(sheetName);

        String moduleName = data.get(0).get("ModuleName");

        homePage.navigateToModule(moduleName);
    }
}

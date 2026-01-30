package com.fms.automation.selenium_cucumber_framework.utils;

import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    // 🔹 Holds currently active Excel file
    private static String excelFileName;

    /**
     * Explicitly set Excel file before reading data
     * Example: ExcelUtil.setExcelFile("AccountPayable_Data.xlsx");
     */
    public static void setExcelFile(String fileName) {
        excelFileName = fileName;
    }

    public static List<Map<String, String>> getTestData(String sheetName) {

        if (excelFileName == null) {
            throw new RuntimeException(
                    "Excel file not set. Call ExcelUtil.setExcelFile() before getTestData()");
        }

        List<Map<String, String>> dataList = new ArrayList<>();

        try {
            String filePath = System.getProperty("user.dir")
                    + "/src/test/resources/testdata/" + excelFileName;

            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                workbook.close();
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                Map<String, String> dataMap = new HashMap<>();

                remember:
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).toString().trim();
                    String value = currentRow.getCell(j).toString().trim();
                    dataMap.put(key, value);
                }

                dataList.add(dataMap);
            }

            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Excel file not loaded properly", e);
        }

        return dataList;
    }
}

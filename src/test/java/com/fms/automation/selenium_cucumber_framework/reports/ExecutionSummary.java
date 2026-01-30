package com.fms.automation.selenium_cucumber_framework.reports;

import java.util.HashMap;
import java.util.Map;

public class ExecutionSummary {

    private static Map<String, Integer> phaseCount = new HashMap<>();
    private static Map<String, Integer> moduleCount = new HashMap<>();
    private static Map<String, Integer> runTypeCount = new HashMap<>();

    private static boolean summaryGenerated = false;

    public static void incrementPhase(String phase) {
        phaseCount.put(phase, phaseCount.getOrDefault(phase, 0) + 1);
    }

    public static void incrementModule(String module) {
        moduleCount.put(module, moduleCount.getOrDefault(module, 0) + 1);
    }

    public static void incrementRunType(String runType) {
        runTypeCount.put(runType, runTypeCount.getOrDefault(runType, 0) + 1);
    }

    public static Map<String, Integer> getPhaseCount() {
        return phaseCount;
    }

    public static Map<String, Integer> getModuleCount() {
        return moduleCount;
    }

    public static Map<String, Integer> getRunTypeCount() {
        return runTypeCount;
    }

    public static boolean isSummaryGenerated() {
        return summaryGenerated;
    }

    public static void markSummaryGenerated() {
        summaryGenerated = true;
    }
}

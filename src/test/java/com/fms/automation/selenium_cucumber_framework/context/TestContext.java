package com.fms.automation.selenium_cucumber_framework.context;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static ThreadLocal<Map<String, Object>> context =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    public static Object get(String key) {
        return context.get().get(key);
    }

    public static String getString(String key) {
        Object value = context.get().get(key);
        return value == null ? null : value.toString();
    }

    public static void clear() {
        context.get().clear();
    }
}

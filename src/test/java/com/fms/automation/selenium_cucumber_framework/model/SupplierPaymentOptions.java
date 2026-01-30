package com.fms.automation.selenium_cucumber_framework.model;

public class SupplierPaymentOptions {

    public boolean bacsPayable;
    public boolean selectPayee;
    public boolean emailRemittance;

    private SupplierPaymentOptions() {
        // force use of factory methods
    }

    public static SupplierPaymentOptions none() {
        SupplierPaymentOptions options = new SupplierPaymentOptions();
        options.bacsPayable = false;
        options.selectPayee = false;
        options.emailRemittance = false;
        return options;
    }

    public static SupplierPaymentOptions bacsOnly() {
        SupplierPaymentOptions options = new SupplierPaymentOptions();
        options.bacsPayable = true;
        options.selectPayee = false;
        options.emailRemittance = false;
        return options;
    }

    public static SupplierPaymentOptions full() {
        SupplierPaymentOptions options = new SupplierPaymentOptions();
        options.bacsPayable = true;
        options.selectPayee = true;
        options.emailRemittance = true;
        return options;
    }
}

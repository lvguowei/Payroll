package main.java.payroll.entities;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Paycheck {
    private double grossPay;
    private double deductions;
    private double netPay;
    private Calendar itsPayPeriodStartDate;
    private Calendar itsPayPeriodEndDate;
    private Map<String, String> fields;

    public Paycheck(Calendar payPeriodStartDate, Calendar payPeriodEndDate) {
        this.itsPayPeriodStartDate = payPeriodStartDate;
        this.itsPayPeriodEndDate = payPeriodEndDate;
        fields = new HashMap<>();
    }

    public double getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public Calendar getPayPeriodEndDate() {
        return itsPayPeriodEndDate;
    }

    public Calendar getPayPeriodStart() {
        return itsPayPeriodStartDate;
    }

    public void setField(String name, String value) {
        fields.put(name, value);
    }

    public String getField(String name) {
        return fields.get(name);
    }
}

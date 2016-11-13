package main.java.payroll.entities;

import java.util.Calendar;

public class SalesReceipt {

    private Calendar itsSaleDate;
    private double itsAmount;

    public SalesReceipt(Calendar date, double amount) {
        this.itsSaleDate = date;
        this.itsAmount = amount;
    }

    public Calendar getDate() {
        return itsSaleDate;
    }

    public double getAmount() {
        return itsAmount;
    }
}

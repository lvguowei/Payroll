package main.java.payroll.entities;

import java.util.Calendar;

public class ServiceCharge {

    private double itsAmount;
    private Calendar itsDate;

    public ServiceCharge(double amount, Calendar date) {
        this.itsAmount = amount;
        this.itsDate = date;
    }

    public double getAmount() {
        return itsAmount;
    }

    public Calendar getDate() {
        return itsDate;
    }
}

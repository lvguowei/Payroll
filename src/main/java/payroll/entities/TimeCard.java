package main.java.payroll.entities;

import java.util.Calendar;

public class TimeCard {
    private Calendar itsDate;
    private double itsHours;

    public TimeCard(Calendar itsDate, double itsHours) {
        this.itsDate = itsDate;
        this.itsHours = itsHours;
    }

    public Calendar getDate() {
        return itsDate;
    }

    public double getHours() {
        return itsHours;
    }
}

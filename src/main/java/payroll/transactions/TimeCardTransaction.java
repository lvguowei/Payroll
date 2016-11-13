package main.java.payroll.transactions;

import main.java.payroll.*;
import main.java.payroll.entities.Employee;
import main.java.payroll.entities.TimeCard;
import main.java.payroll.entities.paymentclassifications.HourlyClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;

import java.util.Calendar;

public class TimeCardTransaction implements Transaction {

    private int itsEmpid;
    private Calendar itsDate;
    private double itsHours;

    public TimeCardTransaction(int empid, Calendar date, double hours) {
        this.itsEmpid = empid;
        this.itsDate = date;
        this.itsHours = hours;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.instance.getEmployee(itsEmpid);
        if (e != null) {
            PaymentClassification pc = e.getClassification();
            if (pc instanceof HourlyClassification) {
                HourlyClassification hc = (HourlyClassification) pc;
                hc.addTimeCard(new TimeCard(itsDate, itsHours));
            } else {
                throw new RuntimeException("Tried to add timecard to non-hourly employee");
            }
        } else {
            throw new RuntimeException("No such employee");
        }
    }
}

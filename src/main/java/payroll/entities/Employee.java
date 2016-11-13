package main.java.payroll.entities;

import main.java.payroll.entities.affiliations.Affiliation;
import main.java.payroll.entities.affiliations.NoAffiliation;
import main.java.payroll.entities.paymentmethods.PaymentMethod;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;

import java.util.Calendar;

public class Employee {

    private int itsEmpId;
    private String itsName;
    private String itsAddress;

    private PaymentClassification itsClassification;
    private PaymentMethod itsMethod;
    private PaymentSchedule itsSchedule;
    private Affiliation itsAffiliation;

    public Employee(int empId, String name, String address) {
        this.itsEmpId = empId;
        this.itsName = name;
        this.itsAddress = address;
        itsAffiliation = new NoAffiliation();
    }

    public PaymentSchedule getSchedule() {return itsSchedule;}
    public void setSchedule(PaymentSchedule schedule) {this.itsSchedule = schedule;}

    public PaymentClassification getClassification() {return itsClassification;}
    public void setClassification(PaymentClassification classification) {
        itsClassification = classification;}

    public PaymentMethod getMethod() {return itsMethod;}
    public void setMethod(PaymentMethod method) {this.itsMethod = method;}

    public Affiliation getAffiliation() {return itsAffiliation;}
    public void setAffiliation(Affiliation affiliation) {this.itsAffiliation = affiliation;}

    public String getName() {return itsName;}
    public void setName(String itsName) {this.itsName = itsName;}

    public String getAddress() {return itsAddress;}
    public void setAddress(String itsAddress) {this.itsAddress = itsAddress;}

    public void payday(Paycheck pc) {
        double grossPay = itsClassification.calculatePay(pc);
        double deduction = itsAffiliation.calculateDeductions(pc);
        double netPay = grossPay - deduction;
        pc.setGrossPay(grossPay);
        pc.setDeductions(deduction);
        pc.setNetPay(netPay);
        itsMethod.pay(pc);
    }

    public boolean isPayDate(Calendar date) {
        return itsSchedule.isPayday(date);
    }

    public Calendar getPayPeriodStartDay(Calendar payPeriodEndDate) {
        return itsSchedule.getPayPeriodStartDate(payPeriodEndDate);
    }
}

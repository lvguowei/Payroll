package main.java.payroll.transactions;

import main.java.payroll.*;
import main.java.payroll.entities.Employee;
import main.java.payroll.entities.SalesReceipt;
import main.java.payroll.entities.paymentclassifications.CommissionedClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;

import java.util.Calendar;

public class SalesReceiptTransaction implements Transaction {

    private Calendar itsSaleDate;
    private double itsAmount;
    private int itsEmpid;

    public SalesReceiptTransaction(Calendar date, double amount, int empid) {
        this.itsSaleDate = date;
        this.itsAmount = amount;
        this.itsEmpid = empid;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.instance.getEmployee(itsEmpid);
        if (e != null) {
            PaymentClassification pc = e.getClassification();
            if (pc instanceof CommissionedClassification) {
                CommissionedClassification cc = (CommissionedClassification) pc;
                cc.addReceipt(new SalesReceipt(itsSaleDate, itsAmount));
            } else {
                throw new RuntimeException("Tried to add sales receipt to non-commissioned employee");
            }
        } else {
            throw new RuntimeException("Employee not found");
        }
    }
}

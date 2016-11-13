package main.java.payroll.entities.paymentclassifications;

import main.java.payroll.entities.Paycheck;
import main.java.payroll.entities.SalesReceipt;

import java.util.*;

public class CommissionedClassification extends PaymentClassification {

    private double itsCommissionRate;
    private double itsSalary;
    private Map<Calendar, SalesReceipt> itsReceipts;

    public CommissionedClassification(double salary, double commissionRate) {
        this.itsCommissionRate = commissionRate;
        this.itsSalary = salary;
        itsReceipts = new HashMap<>();
    }

    public double getCommissionRate() {
        return itsCommissionRate;
    }

    public double getSalary() {
        return itsSalary;
    }

    public void addReceipt(SalesReceipt receipt) {
        itsReceipts.put(receipt.getDate(), receipt);
    }

    public SalesReceipt getSalesReceipt(Calendar date) {
        return itsReceipts.get(date);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double commission = 0.0;
        for (Calendar c : itsReceipts.keySet()) {
            if (isInPayPeriod(c, pc)) {
                commission += itsReceipts.get(c).getAmount() * itsCommissionRate;
            }
        }
        return itsSalary + commission;
    }
}

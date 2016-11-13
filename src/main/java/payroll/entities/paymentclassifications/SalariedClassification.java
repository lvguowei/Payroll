package main.java.payroll.entities.paymentclassifications;

import main.java.payroll.entities.Paycheck;

public class SalariedClassification extends PaymentClassification {

    private double itsSalary;

    public SalariedClassification(double salary) {
        this.itsSalary = salary;
    }

    public double getSalary() {
        return itsSalary;
    }

    @Override
    public double calculatePay(Paycheck pc) {
        return itsSalary;
    }
}

package main.java.payroll.transactions;

import main.java.payroll.entities.paymentschedule.BiweeklySchedule;
import main.java.payroll.entities.paymentclassifications.CommissionedClassification;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {

    private double itsSalary;
    private double itsCommissionRate;

    public ChangeCommissionedTransaction(int empId, double salary, double rate) {
        super(empId);
        this.itsSalary = salary;
        this.itsCommissionRate = rate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new CommissionedClassification(itsSalary, itsCommissionRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}

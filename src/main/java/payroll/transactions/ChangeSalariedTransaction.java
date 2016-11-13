package main.java.payroll.transactions;

import main.java.payroll.entities.paymentschedule.MonthlySchedule;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;
import main.java.payroll.entities.paymentclassifications.SalariedClassification;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {

    private double itsSalary;

    public ChangeSalariedTransaction(int empId, double salary) {
        super(empId);
        this.itsSalary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(itsSalary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}

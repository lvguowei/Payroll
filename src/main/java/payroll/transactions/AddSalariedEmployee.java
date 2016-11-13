package main.java.payroll.transactions;

import main.java.payroll.entities.paymentschedule.MonthlySchedule;
import main.java.payroll.entities.paymentclassifications.PaymentClassification;
import main.java.payroll.entities.paymentschedule.PaymentSchedule;
import main.java.payroll.entities.paymentclassifications.SalariedClassification;

public class AddSalariedEmployee extends AddEmployeeTransaction {

    private double itsSalary;

    public AddSalariedEmployee(int empid, String name, String address, double salary) {
        super(empid, name, address);
        this.itsSalary = salary;
    }

    @Override
    PaymentClassification getClassification() {
        return new SalariedClassification(itsSalary);
    }

    @Override
    PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
